package kh.com.acledabank.camdx;

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
import kh.com.acledabank.CamdxProperties;
import kh.com.acledabank.TelegramProperties;
import kh.com.acledabank.utility.CurrentDateTime;
import kh.com.acledabank.utility.Telegram;

@Service
public class CamdxService {

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	Telegram telegram;
	
	private final Logger log = LoggerFactory.getLogger(CamdxService.class);
	private String url;
	private String teleUrl;
	private String chatId;
	private String threadId;
	private String serverSide;
	private String service;
	private String reqDatetime = null, resDatetime = null;
	
	public CamdxService(CamdxProperties pCamdx, TelegramProperties pTele) {
		this.url = pCamdx.getEndpoint();
		this.teleUrl = pTele.getEndpoint();
		this.chatId = pTele.getChatid();
		this.threadId = pTele.getThreadid();
		this.serverSide = pTele.getServerside();
		this.service = pTele.getService();
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
			
			ResponseEntity resEntity = restTemplate.exchange(url + "/info-v2.1", HttpMethod.POST, fullReq, String.class);
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
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	public ResponseEntity VerifyUserInfoFace(String request, String headerValue, String reqID) {
		ResponseEntity res = null;
		HttpHeaders headers = new HttpHeaders();
		try {
			headers.add("Content-Type", "application/json");
			headers.add("X-Road-Client", headerValue);
			headers.add("Connection", "close");
			HttpEntity<String> fullReq = new HttpEntity<String>(request, headers);
			log.info("=======> VerifyUserInfoFace RequestID=" + reqID + " => VerifyUserInfoFace Request Body: " + request + "\r\n");
			
//			Track request datetime
			reqDatetime = CurrentDateTime.getCurrentDateTime();
			
			ResponseEntity resEntity = restTemplate.exchange(url + "/info-face-v2.1", HttpMethod.POST, fullReq, String.class);
			log.info("=======> VerifyUserInfoFace Response(RequestID=" + reqID +") => " + resEntity.getBody().toString() + "\r\n");
			res = resEntity;
		} catch (HttpStatusCodeException e) {
			log.info("=======> VerifyUserInfoFace Response(RequestID=" + reqID +") => CODE:" + e.getStatusCode() + ", BODY:" + e.getResponseBodyAsString() + "\r\n");
			
//			Track response datetime
			resDatetime = CurrentDateTime.getCurrentDateTime();
			
//			======== Telegram Alert ========
			String function = "VerifyUserInfoFace";
			telegram.alertMessage(teleUrl, chatId, threadId, serverSide, service, function, reqID, request, reqDatetime, resDatetime, "Error", e.getStatusCode().toString(), e.getResponseBodyAsString());
//			======== End ========
			
			return new ResponseEntity(e.getResponseBodyAsString(), e.getStatusCode());
		} catch (Exception e) {
			log.error("=======> VerifyUserInfoFace Response(RequestID=" + reqID +") => " + e + " => BODY:{\"status\": \"Exception Error\",\"message\": \"" + e.getCause().getMessage() + "\"}" + "\r\n");
			
//			Track response datetime
			resDatetime = CurrentDateTime.getCurrentDateTime();
			
//			======== Telegram Alert ========
			String function = "VerifyUserInfoFace";
			telegram.alertMessage(teleUrl, chatId, threadId, serverSide, service, function, reqID, request, reqDatetime, resDatetime, "Error", ((HttpStatusCodeException) e).getStatusCode().toString(), ((RestClientResponseException) e).getResponseBodyAsString());
//			======== End ========
			
			return new ResponseEntity("{\"status\": \"Exception Error\",\"message\": \"" + e.getCause().getMessage() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return res;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	public ResponseEntity VerifyUserInfoFaceIdcard(String request, String headerValue, String reqID) {
		ResponseEntity res = null;
		HttpHeaders headers = new HttpHeaders();
		try {
			headers.add("Content-Type", "application/json");
			headers.add("X-Road-Client", headerValue);
			headers.add("Connection", "close");
			HttpEntity<String> fullReq = new HttpEntity<String>(request, headers);
			log.info("=======> VerifyUserInfoFaceIdcard RequestID=" + reqID + " => VerifyUserInfoFaceIdcard Request Body: " + request + "\r\n");
			
//			Track request datetime
			reqDatetime = CurrentDateTime.getCurrentDateTime();
			
			ResponseEntity resEntity = restTemplate.exchange(url + "/info-face-idcard-v2.1", HttpMethod.POST, fullReq, String.class);
			log.info("=======> VerifyUserInfoFaceIdcard Response(RequestID=" + reqID +") => " + resEntity.getBody().toString() + "\r\n");
			res = resEntity;
		} catch (HttpStatusCodeException e) {
			//e.printStackTrace();
			log.error("=======> VerifyUserInfoFaceIdcard Response(RequestID=" + reqID +") => CODE:" + e.getStatusCode() + ", BODY:" + e.getResponseBodyAsString() + "\r\n");
			
//			Track response datetime
			resDatetime = CurrentDateTime.getCurrentDateTime();
			
//			======== Telegram Alert ========
			String function = "VerifyUserInfoFaceIdcard";
			telegram.alertMessage(teleUrl, chatId, threadId, serverSide, service, function, reqID, request, reqDatetime, resDatetime, "Error", e.getStatusCode().toString(), e.getResponseBodyAsString());
//			======== End ========
			
			return new ResponseEntity(e.getResponseBodyAsString(), e.getStatusCode());
		} catch (Exception e) {
			log.error("=======> VerifyUserInfoFaceIdcard Response(RequestID=" + reqID +") => " + e + " => BODY:{\"status\": \"Exception Error\",\"message\": \"" + e.getCause().getMessage() + "\"}" + "\r\n");
			
//			Track response datetime
			resDatetime = CurrentDateTime.getCurrentDateTime();
			
//			======== Telegram Alert ========
			String function = "VerifyUserInfoFaceIdcard";
			telegram.alertMessage(teleUrl, chatId, threadId, serverSide, service, function, reqID, request, reqDatetime, resDatetime, "Error", ((HttpStatusCodeException) e).getStatusCode().toString(), ((RestClientResponseException) e).getResponseBodyAsString());
//			======== End ========
			
			return new ResponseEntity("{\"status\": \"Exception Error\",\"message\": \"" + e.getCause().getMessage() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return res;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	public ResponseEntity VerifyUserInfoIdcardLiveness(String request, String headerValue, String reqID) {
		ResponseEntity res = null;
		HttpHeaders headers = new HttpHeaders();
		try {
			headers.add("Content-Type", "application/json");
			headers.add("X-Road-Client", headerValue);
			headers.add("Connection", "close");
			HttpEntity<String> fullReq = new HttpEntity<String>(request, headers);
			log.info("=======> VerifyUserInfoIdcardLiveness RequestID=" + reqID + " => VerifyUserInfoIdcardLiveness Request Body: " + request + "\r\n");
			
//			Track request datetime
			reqDatetime = CurrentDateTime.getCurrentDateTime();
			
			ResponseEntity resEntity = restTemplate.exchange(url + "/info-idcard-liveness-v2.1", HttpMethod.POST, fullReq, String.class);
			log.info("=======> VerifyUserInfoIdcardLiveness Response(RequestID=" + reqID +") => " + resEntity.getBody().toString() + "\r\n");
			res = resEntity;
		} catch (HttpStatusCodeException e) {
			//e.printStackTrace();
			log.error("=======> VerifyUserInfoIdcardLiveness Response(RequestID=" + reqID +") => CODE:" + e.getStatusCode() + ", BODY:" + e.getResponseBodyAsString() + "\r\n");
			
//			Track response datetime
			resDatetime = CurrentDateTime.getCurrentDateTime();
			
//			======== Telegram Alert ========
			String function = "VerifyUserInfoIdcardLiveness";
			telegram.alertMessage(teleUrl, chatId, threadId, serverSide, service, function, reqID, request, reqDatetime, resDatetime, "Error", e.getStatusCode().toString(), e.getResponseBodyAsString());
//			======== End ========
			
			return new ResponseEntity(e.getResponseBodyAsString(), e.getStatusCode());
		} catch (Exception e) {
			log.error("=======> VerifyUserInfoIdcardLiveness Response(RequestID=" + reqID +") => " + e + " => BODY:{\"status\": \"Exception Error\",\"message\": \"" + e.getCause().getMessage() + "\"}" + "\r\n");
			
//			Track response datetime
			resDatetime = CurrentDateTime.getCurrentDateTime();
			
//			======== Telegram Alert ========
			String function = "VerifyUserInfoIdcardLiveness";
			telegram.alertMessage(teleUrl, chatId, threadId, serverSide, service, function, reqID, request, reqDatetime, resDatetime, "Error", ((HttpStatusCodeException) e).getStatusCode().toString(), ((RestClientResponseException) e).getResponseBodyAsString());
//			======== End ========
			
			return new ResponseEntity("{\"status\": \"Exception Error\",\"message\": \"" + e.getCause().getMessage() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return res;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	public ResponseEntity OcrIdcard(String request, String headerValue, String reqID) {
		ResponseEntity res = null;
		HttpHeaders headers = new HttpHeaders();
		try {
			headers.add("Content-Type", "application/json");
			headers.add("X-Road-Client", headerValue);
			headers.add("Connection", "close");
			HttpEntity<String> fullReq = new HttpEntity<String>(request, headers);
			log.info("=======> OcrIdcard RequestID=" + reqID + " => OcrIdcard Request Body: " + request + "\r\n");
			
//			Track request datetime
			reqDatetime = CurrentDateTime.getCurrentDateTime();
			
			ResponseEntity resEntity = restTemplate.exchange(url + "/ocr-idcard-v2.1", HttpMethod.POST, fullReq, String.class);
			log.info("=======> OcrIdcard Response(RequestID=" + reqID +") => " + resEntity.getBody().toString() + "\r\n");
			res = resEntity;
		} catch (HttpStatusCodeException e) {
			//e.printStackTrace();
			log.error("=======> OcrIdcard Response(RequestID=" + reqID +") => CODE:" + e.getStatusCode() + ", BODY:" + e.getResponseBodyAsString() + "\r\n");
			
//			Track response datetime
			resDatetime = CurrentDateTime.getCurrentDateTime();
			
//			======== Telegram Alert ========
			String function = "OcrIdcard";
			telegram.alertMessage(teleUrl, chatId, threadId, serverSide, service, function, reqID, request, reqDatetime, resDatetime, "Error", e.getStatusCode().toString(), e.getResponseBodyAsString());
//			======== End ========
			
			return new ResponseEntity(e.getResponseBodyAsString(), e.getStatusCode());
		} catch (Exception e) {
			log.error("=======> OcrIdcard Response(RequestID=" + reqID +") => " + e + " => BODY:{\"status\": \"Exception Error\",\"message\": \"" + e.getCause().getMessage() + "\"}" + "\r\n");
			
//			Track response datetime
			resDatetime = CurrentDateTime.getCurrentDateTime();
			
//			======== Telegram Alert ========
			String function = "OcrIdcard";
			telegram.alertMessage(teleUrl, chatId, threadId, serverSide, service, function, reqID, request, reqDatetime, resDatetime, "Error", ((HttpStatusCodeException) e).getStatusCode().toString(), ((RestClientResponseException) e).getResponseBodyAsString());
//			======== End ========
			
			return new ResponseEntity("{\"status\": \"Exception Error\",\"message\": \"" + e.getCause().getMessage() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return res;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ResponseBody
	public ResponseEntity OcrPassport(String request, String headerValue, String reqID) {
		ResponseEntity res = null;
		HttpHeaders headers = new HttpHeaders();
		try {
			headers.add("Content-Type", "application/json");
			headers.add("X-Road-Client", headerValue);
			headers.add("Connection", "close");
			HttpEntity<String> fullReq = new HttpEntity<String>(request, headers);
			log.info("=======> OcrPassport RequestID=" + reqID + " => OcrPassport Request Body: " + request + "\r\n");
			
//			Track request datetime
			reqDatetime = CurrentDateTime.getCurrentDateTime();
			
			ResponseEntity resEntity = restTemplate.exchange(url + "/ocr-passport-v2.1", HttpMethod.POST, fullReq, String.class);
			log.info("=======> OcrPassport Response(RequestID=" + reqID +") => " + resEntity.getBody().toString() + "\r\n");
			res = resEntity;
		} catch (HttpStatusCodeException e) {
			//e.printStackTrace();
			log.error("=======> OcrPassport Response(RequestID=" + reqID +") => CODE:" + e.getStatusCode() + ", BODY:" + e.getResponseBodyAsString() + "\r\n");
			
//			Track response datetime
			resDatetime = CurrentDateTime.getCurrentDateTime();
			
//			======== Telegram Alert ========
			String function = "OcrPassport";
			telegram.alertMessage(teleUrl, chatId, threadId, serverSide, service, function, reqID, request, reqDatetime, resDatetime, "Error", e.getStatusCode().toString(), e.getResponseBodyAsString());
//			======== End ========
			
			return new ResponseEntity(e.getResponseBodyAsString(), e.getStatusCode());
		} catch (Exception e) {
			log.error("=======> OcrPassport Response(RequestID=" + reqID +") => " + e + " => BODY:{\"status\": \"Exception Error\",\"message\": \"" + e.getCause().getMessage() + "\"}" + "\r\n");
			
//			Track response datetime
			resDatetime = CurrentDateTime.getCurrentDateTime();
			
//			======== Telegram Alert ========
			String function = "OcrPassport";
			telegram.alertMessage(teleUrl, chatId, threadId, serverSide, service, function, reqID, request, reqDatetime, resDatetime, "Error", ((HttpStatusCodeException) e).getStatusCode().toString(), ((RestClientResponseException) e).getResponseBodyAsString());
//			======== End ========
			
			return new ResponseEntity("{\"status\": \"Exception Error\",\"message\": \"" + e.getCause().getMessage() + "\"}", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return res;
	}
}
