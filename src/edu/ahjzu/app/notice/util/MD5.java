package edu.ahjzu.app.notice.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	public static String getMD5(String content) {
		MessageDigest digest = null;
		if (content != null) {
			try {
				digest = MessageDigest.getInstance("MD5");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			digest.update(content.getBytes());
		}
		return getHashString(digest);
	}

	private static String getHashString(MessageDigest digest) {
		if (digest != null) {
			StringBuilder builder = new StringBuilder();
			for (byte b : digest.digest()) {
				builder.append(Integer.toHexString((b >> 4) & 0xf));
				builder.append(Integer.toHexString(b & 0xf));
			}
			return builder.toString();
		} else {
			return "";
		}
	}
}
