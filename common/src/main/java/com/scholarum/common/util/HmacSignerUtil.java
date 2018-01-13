package com.scholarum.common.util;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.eclipse.jetty.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.scholarum.common.exception.ScException;

@Component
public class HmacSignerUtil {

	private static final String SIGN_ALGO = "HmacSHA1";

	public String signWithSecretKey(String secret, String data) {
		byte[] signature;
		try {
			SecretKeySpec k = new SecretKeySpec(secret.getBytes(), SIGN_ALGO);
			Mac mac = Mac.getInstance(SIGN_ALGO);
			mac.init(k);
			signature = new Hex().encode(mac.doFinal(data.getBytes()));
		} catch (Exception ex) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, ex.getMessage());
		}
		try {
			return new String(signature, "UTF-8");
		} catch (Exception ex) {
			throw new ScException(HttpStatus.BAD_REQUEST_400, ex.getMessage());
		}
	}

}
