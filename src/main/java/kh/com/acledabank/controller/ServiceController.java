package kh.com.acledabank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kh.com.acledabank.service.BackendService;

@RestController
public class ServiceController {

	@Autowired
	BackendService backend;
	
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "/cr-web-backend/api/v1/getContentById", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity GetContentById(@RequestParam("id") String id, @RequestParam(name = "lang", required = false) String lang) {
		if (lang == null) {
			lang = "en";
		}
		return backend.GetContentById(id, lang);
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity Info(@RequestBody String request, @RequestHeader(value = "X-Road-Client", required = true) String header, @RequestHeader(value = "ReqID", required = true) String reqID) {
		
		return backend.VerifyUserInfo(request, header, reqID);
	}
	
}
