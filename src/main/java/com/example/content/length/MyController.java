package com.example.content.length;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

	@RequestMapping("/**")
	public ResponseEntity<?> handleReq(RequestEntity<byte[]> reqEntity, @RequestHeader HttpHeaders reqHeaders) {
		HttpHeaders responseHeaderMap = new HttpHeaders();
		responseHeaderMap.set("abc11", "xyz11");
		responseHeaderMap.set("abc12", "xyz12");
		responseHeaderMap.set("abc13", "xyz13");
		responseHeaderMap.set("abc14", "xyz14");

		return (new ResponseEntity<byte[]>(responseHeaderMap, HttpStatus.NO_CONTENT));
	}
}
