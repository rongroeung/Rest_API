package kh.com.acledabank.controller;

import org.apache.commons.lang3.StringEscapeUtils;
import org.owasp.encoder.Encode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import kh.com.acledabank.camdx.CamdxService;

@SuppressWarnings("deprecation")
@RestController
public class ServiceController {

	@Autowired
	CamdxService camdx;
	
	private String validateAndSanitize(String input) {
	    // Perform input validation and sanitization here
	    // You can use libraries like OWASP Java Encoder or JSoup for sanitization

	    // Example using OWASP Java Encoder
	    String sanitizedInput = Encode.forHtml(input);
	    sanitizedInput = StringEscapeUtils.unescapeHtml4(sanitizedInput);
	    
	    return sanitizedInput;
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity Info(@RequestBody String request, @RequestHeader(value = "X-Road-Client", required = true) String header, @RequestHeader(value = "ReqID", required = true) String reqID) {
		
		return camdx.VerifyUserInfo(validateAndSanitize(request), validateAndSanitize(header), validateAndSanitize(reqID));
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/info-face", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity InfoFace(@RequestBody String request, @RequestHeader(value = "X-Road-Client", required = true) String header, @RequestHeader(value = "ReqID", required = true) String reqID) {
		
		return camdx.VerifyUserInfoFace(validateAndSanitize(request), validateAndSanitize(header), validateAndSanitize(reqID));
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/info-face-idcard", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity InfoFaceIdcard(@RequestBody String request, @RequestHeader(value = "X-Road-Client", required = true) String header, @RequestHeader(value = "ReqID", required = true) String reqID) {
		
		return camdx.VerifyUserInfoFaceIdcard(validateAndSanitize(request), validateAndSanitize(header), validateAndSanitize(reqID));
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/info-idcard-liveness", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity InfoIdcardLiveness(@RequestBody String request, @RequestHeader(value = "X-Road-Client", required = true) String header, @RequestHeader(value = "ReqID", required = true) String reqID) {
		
		return camdx.VerifyUserInfoIdcardLiveness(validateAndSanitize(request), validateAndSanitize(header), validateAndSanitize(reqID));
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/ocr-idcard", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity OcrIdcard(@RequestBody String request, @RequestHeader(value = "X-Road-Client", required = true) String header, @RequestHeader(value = "ReqID", required = true) String reqID) {
		
		return camdx.OcrIdcard(validateAndSanitize(request), validateAndSanitize(header), validateAndSanitize(reqID));
	}
	
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "/ocr-passport", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity OcrPassport(@RequestBody String request, @RequestHeader(value = "X-Road-Client", required = true) String header, @RequestHeader(value = "ReqID", required = true) String reqID) {
		
		return camdx.OcrPassport(validateAndSanitize(request), validateAndSanitize(header), validateAndSanitize(reqID));
	}
}
