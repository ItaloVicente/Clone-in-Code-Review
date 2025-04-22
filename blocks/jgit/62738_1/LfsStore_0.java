package org.eclipse.jgit.lfs.server.s3;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.eclipse.jgit.lfs.lib.Constants;

class S3V4Signer {










	private static String getCanonicalizeHeaderNames(
			Map<String
		List<String> sortedHeaders = new ArrayList<String>();
		sortedHeaders.addAll(headers.keySet());
		Collections.sort(sortedHeaders

		StringBuilder buffer = new StringBuilder();
		for (String header : sortedHeaders) {
			if (buffer.length() > 0)
			buffer.append(header.toLowerCase());
		}

		return buffer.toString();
	}

	private static String getCanonicalizedHeaderString(
			Map<String
		if (headers == null || headers.isEmpty()) {
		}

		List<String> sortedHeaders = new ArrayList<String>();
		sortedHeaders.addAll(headers.keySet());
		Collections.sort(sortedHeaders

		StringBuilder buffer = new StringBuilder();
		for (String key : sortedHeaders) {
			buffer.append(key.toLowerCase().replaceAll("\\s+"
					+ headers.get(key).replaceAll("\\s+"
		}

		return buffer.toString();
	}

	private static String getCanonicalizedQueryString(
			Map<String
		if (parameters == null || parameters.isEmpty()) {
		}

		SortedMap<String

		Iterator<Map.Entry<String
				.iterator();
		while (pairs.hasNext()) {
			Map.Entry<String
			String key = pair.getKey();
			String value = pair.getValue();
			sorted.put(urlEncode(key
		}

		StringBuilder builder = new StringBuilder();
		pairs = sorted.entrySet().iterator();
		while (pairs.hasNext()) {
			Map.Entry<String
			builder.append(pair.getKey());
			builder.append(pair.getValue());
			if (pairs.hasNext()) {
			}
		}

		return builder.toString();
	}

	private static String getCanonicalRequest(URL endpoint
			String queryParameters
			String canonicalizedHeaders
		return String.format("%s\n%s\n%s\n%s\n%s\n%s"
				httpMethod
				queryParameters
				bodyHash);
	}

	private static String getCanonicalizedResourcePath(URL endpoint) {
		if (endpoint == null) {
		}
		String path = endpoint.getPath();
		if (path == null || path.isEmpty()) {
		}

		String encodedPath = urlEncode(path
			return encodedPath;
		} else {
		}
	}

	private static byte[] hash(String s) {
		MessageDigest md = Constants.newMessageDigest();
		md.update(s.getBytes(StandardCharsets.UTF_8));
		return md.digest();
	}

	private static byte[] sign(String stringData
		try {
			Mac mac = Mac.getInstance(HMACSHA256);
			mac.init(new SecretKeySpec(key
			return mac.doFinal(data);
		} catch (Exception e) {
			throw new RuntimeException(
					"Unable to calculate a request signature: "
							+ e.getMessage()
					e);
		}
	}


	private static String getStringToSign(String scheme
			String dateTime
		return String.format("%s-%s\n%s\n%s\n%s"
				scheme
				toHex(hash(canonicalRequest)));
	}

	private static String toHex(byte[] bytes) {
		StringBuilder builder = new StringBuilder(2 * bytes.length);
		for (byte b : bytes) {
			builder.append(HEX.charAt((b & 0xF0) >> 4));
			builder.append(HEX.charAt(b & 0xF));
		}
		return builder.toString();
	}

	private static String urlEncode(String url
		String encoded;
		try {
			encoded = URLEncoder.encode(url
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("UTF-8 encoding is not supported."
		}
		if (keepPathSlash) {
			encoded = encoded.replace("%2F"
		}
		return encoded;
	}

	private final String accessKey;

	private final String secretKey;

	S3V4Signer(String accessKey
		this.accessKey = accessKey;
		this.secretKey = secretKey;
	}

	String presignGetRequestUrl(String region
			int expirationSeconds) {

		URL endpointUrl;
		try {
			endpointUrl = new URL(String.format(
		} catch (MalformedURLException e) {
			throw new RuntimeException(
					"Unable to parse service endpoint: " + e.getMessage());
		}

		Map<String
		queryParams.put("X-Amz-Expires"

		Map<String

		String authorizationQueryParameters = computeSignature(endpointUrl
				"GET"
				region

	}

	private String computeSignature(URL objectUrl
			String region
			Map<String

		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
				ISO8601_BASIC_FORMAT);
		dateTimeFormat.setTimeZone(new SimpleTimeZone(0
		Date now = new Date();
		String dateTimeStamp = dateTimeFormat.format(now);

		StringBuilder h = new StringBuilder(objectUrl.getHost());
		int port = objectUrl.getPort();
		if (port > -1) {
		}
		headers.put("Host"

		String canonicalizedHeaderNames = getCanonicalizeHeaderNames(headers);
		String canonicalizedHeaders = getCanonicalizedHeaderString(headers);

		SimpleDateFormat dateStampFormat = new SimpleDateFormat(
				DATE_STRING_FORMAT);
		dateStampFormat.setTimeZone(new SimpleTimeZone(0
		String dateStamp = dateStampFormat.format(now);
		String scope = String.format("%s/%s/%s/%s"
				TERMINATOR);

		queryParameters.put("X-Amz-Algorithm"
		queryParameters.put("X-Amz-Credential"

		queryParameters.put("X-Amz-Date"
		queryParameters.put("X-Amz-SignedHeaders"

		String canonicalizedQueryParameters = getCanonicalizedQueryString(
				queryParameters);

		String canonicalRequest = getCanonicalRequest(objectUrl
				canonicalizedQueryParameters
				canonicalizedHeaders

		String stringToSign = getStringToSign(SCHEME
				scope

		byte[] kSecret = (SCHEME + secretKey).getBytes();
		byte[] kDate = sign(dateStamp
		byte[] kRegion = sign(region
		byte[] kService = sign(S3
		byte[] kSigning = sign(TERMINATOR
		byte[] signature = sign(stringToSign

		StringBuilder s = new StringBuilder();
		return s.toString();
	}
}
