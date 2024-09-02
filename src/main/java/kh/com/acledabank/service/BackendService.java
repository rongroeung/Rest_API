package kh.com.acledabank.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import kh.com.acledabank.DbProperties;
import kh.com.acledabank.TelegramProperties;
import kh.com.acledabank.builder.getContentById.GetContentByIdDescriptionResponseDto;
import kh.com.acledabank.builder.getContentById.GetContentByIdMediaResponseDto;
import kh.com.acledabank.builder.getContentById.GetContentByIdResponse;
import kh.com.acledabank.builder.getContentById.GetContentByIdResponseDto;
import kh.com.acledabank.builder.getContentById.GetContentByIdYoutubeResponseDto;
import kh.com.acledabank.utility.CurrentDateTime;
import kh.com.acledabank.utility.Telegram;

@Service
public class BackendService {

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	Telegram telegram;
	
	private final Logger log = LoggerFactory.getLogger(BackendService.class);
	private String url;
	private String username;
	private String password;
	private String teleUrl;
	private String chatId;
	private String threadId;
	private String serverSide;
	private String service;
	private String reqDatetime = null, resDatetime = null;
	
	public BackendService(DbProperties pDb, TelegramProperties pTele) {
		this.url = pDb.getEndpoint();
		this.username = pDb.getUsername();
		this.password = pDb.getPassword();
		this.teleUrl = pTele.getEndpoint();
		this.chatId = pTele.getChatid();
		this.threadId = pTele.getThreadid();
		this.serverSide = pTele.getServerside();
		this.service = pTele.getService();
	}
	
	@SuppressWarnings({ "rawtypes" })
	@ResponseBody
	public ResponseEntity GetContentById(String id, String lang) {
		GetContentByIdResponse response = new GetContentByIdResponse();
		GetContentByIdResponseDto responseDto = new GetContentByIdResponseDto();
		ArrayList<GetContentByIdDescriptionResponseDto> descriptions = new ArrayList<>();
		ArrayList<GetContentByIdMediaResponseDto> medium = new ArrayList<>();
		ArrayList<GetContentByIdYoutubeResponseDto> youtubes = new ArrayList<>();
		
		String queryContent = "SELECT * FROM tbl_content WHERE id = '" + id + "'";
		String queryDescription = "SELECT * FROM tbl_description WHERE content_id = '" + id + "'";
		String queryMedia = "SELECT * FROM tbl_media WHERE content_id = '" + id + "'";
		String queryYoutube = "SELECT * FROM tbl_youtube WHERE content_id = '" + id + "'";
		
		String language = "";
		if (lang.equals("kh")) {
			language = "kh_";
		}
		
		// Get Content from DB
		try {
			
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(url, username, password);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(queryContent);
			
			while (resultSet.next()) {
				responseDto.setId(resultSet.getString("id"));
				responseDto.setTitle(resultSet.getString(language + "title"));
				responseDto.setSub_title(resultSet.getString(language + "sub_title"));
			}
			
			statement.close();
			connection.close();

		} catch (SQLException | ClassNotFoundException e) {
			response.setCode(500);
			response.setMessage(e.getMessage());
			return new ResponseEntity<GetContentByIdResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		// Validate content id not found
		if (responseDto.getId() == null) {
			response.setCode(404);
			response.setMessage("Content ID not found");
			return new ResponseEntity<GetContentByIdResponse>(response, HttpStatus.NOT_FOUND);
		}
		
		// Get Description from DB
		try {
			
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(url, username, password);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(queryDescription);
			
			while (resultSet.next()) {
				GetContentByIdDescriptionResponseDto description = new GetContentByIdDescriptionResponseDto();
				description.setId(resultSet.getString("id"));
				description.setText(resultSet.getString(language + "text"));
				descriptions.add(description);
			}
			
			statement.close();
			connection.close();
			
		} catch (SQLException | ClassNotFoundException e) {
			response.setCode(500);
			response.setMessage(e.getMessage());
			return new ResponseEntity<GetContentByIdResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		// Get Media from DB
		try {
			
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(url, username, password);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(queryMedia);
			
			while (resultSet.next()) {
				GetContentByIdMediaResponseDto media = new GetContentByIdMediaResponseDto();
				media.setId(resultSet.getString("id"));
				media.setUrl(resultSet.getString("url"));
				media.setName(resultSet.getString("name"));
				medium.add(media);
			}
			
			statement.close();
			connection.close();
			
		} catch (SQLException | ClassNotFoundException e) {
			response.setCode(500);
			response.setMessage(e.getMessage());
			return new ResponseEntity<GetContentByIdResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		// Get Youtube from DB
		try {
			
			Class.forName("org.postgresql.Driver");
			Connection connection = DriverManager.getConnection(url, username, password);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(queryYoutube);
			
			while (resultSet.next()) {
				GetContentByIdYoutubeResponseDto youtube = new GetContentByIdYoutubeResponseDto();
				youtube.setId(resultSet.getString("id"));
				youtube.setTitle(resultSet.getString(language + "title"));
				youtube.setVideo_url(resultSet.getString("video_url"));
				youtube.setDuration(resultSet.getString("duration"));
				youtube.setPublish_date(resultSet.getString("publish_date"));
				youtube.setThumbnail_url(resultSet.getString("thumbnail_url"));
				youtube.setThumbnail_name(resultSet.getString("thumbnail_name"));
				youtubes.add(youtube);
			}
			
			statement.close();
			connection.close();
			
		} catch (SQLException | ClassNotFoundException e) {
			response.setCode(500);
			response.setMessage(e.getMessage());
			return new ResponseEntity<GetContentByIdResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		responseDto.setLang(lang);
		responseDto.setDescription(descriptions);
		responseDto.setMedia(medium);
		responseDto.setYoutube(youtubes);
		response.setCode(200);
		response.setMessage("Success");
		response.setData(responseDto);
		
		return new ResponseEntity<GetContentByIdResponse>(response, HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	public ResponseEntity VerifyUserInfo(String request, String headerValue, String reqID) {
		ResponseEntity res = null;
		HttpHeaders headers = new HttpHeaders();
		
		try {
			headers.add("Content-Type", "application/json");
			headers.add("X-Road-Client", headerValue);
			headers.add("Connection", "close");
			HttpEntity<String> fullReq = new HttpEntity<String>(request, headers);
			log.info("=======> VerifyUserInfo RequestID=" + reqID + " => VerifyUserInfo Request Body: " + request + "\r\n");
			
//			Track request datetime
			reqDatetime = CurrentDateTime.getCurrentDateTime();
			
			ResponseEntity resEntity = restTemplate.exchange("" + "/info-v2.1", HttpMethod.POST, fullReq, String.class);
			log.info("=======> VerifyUserInfo Response(RequestID=" + reqID +") => " + resEntity.getBody().toString() + "\r\n");
			res = resEntity;
		} catch (HttpStatusCodeException e) {
			log.info("=======> VerifyUserInfo Response(RequestID=" + reqID +") => CODE:" + e.getStatusCode() + ", BODY:" + e.getResponseBodyAsString() + "\r\n");
			
//			Track response datetime
			resDatetime = CurrentDateTime.getCurrentDateTime();
			
//			======== Telegram Alert ========
			String function = "VerifyUserInfo";
			telegram.alertMessage(teleUrl, chatId, threadId, serverSide, service, function, reqID, request, reqDatetime, resDatetime, "Error", e.getStatusCode().toString(), e.getResponseBodyAsString());
//			======== End ========
			
			return new ResponseEntity(e.getResponseBodyAsString(), e.getStatusCode());
		} catch (Exception e) {
			log.error("=======> VerifyUserInfo Response(RequestID=" + reqID +") => " + e + " => BODY:{\"status\": \"Exception Error\",\"message\": \"" + e.getCause().getMessage() + "\"}" + "\r\n");
			
//			Track response datetime
			resDatetime = CurrentDateTime.getCurrentDateTime();
			
//			======== Telegram Alert ========
			String function = "VerifyUserInfo";
			telegram.alertMessage(teleUrl, chatId, threadId, serverSide, service, function, reqID, request, reqDatetime, resDatetime, "Error", ((HttpStatusCodeException) e).getStatusCode().toString(), ((RestClientResponseException) e).getResponseBodyAsString());
//			======== End ========
			
			return new ResponseEntity("{\"status\": \"Exception Error\",\"message\": \"" + e.getCause().getMessage() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return res;
	}
}
