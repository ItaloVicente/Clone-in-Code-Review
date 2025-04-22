package org.eclipse.jgit.lfs.server.s3;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.util.HttpSupport.HDR_AUTHORIZATION;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.eclipse.jgit.lfs.lib.Constants;
import org.eclipse.jgit.lfs.server.internal.LfsServerText;

class SignerV4 {



	static String createAuthorizationQuery(S3Config bucketConfig
			String httpMethod
			Map<String
		addHostHeader(url

		queryParameters.put(X_AMZ_ALGORITHM

		Date now = new Date();
		String dateStamp = dateStamp(now);
		String scope = scope(bucketConfig.getRegion()
		queryParameters.put(X_AMZ_CREDENTIAL

		String dateTimeStampISO8601 = dateTimeStampISO8601(now);
		queryParameters.put(X_AMZ_DATE

		String canonicalizedHeaderNames = canonicalizeHeaderNames(headers);
		queryParameters.put(X_AMZ_SIGNED_HEADERS

		String canonicalizedQueryParameters = canonicalizeQueryString(
				queryParameters);
		String canonicalizedHeaders = canonicalizeHeaderString(headers);
		String canonicalRequest = canonicalRequest(url
				canonicalizedQueryParameters
				canonicalizedHeaders
		byte[] signature = createSignature(bucketConfig
				dateStamp
		queryParameters.put(X_AMZ_SIGNATURE

		return formatAuthorizationQuery(queryParameters);
	}

	private static String formatAuthorizationQuery(
			Map<String
		StringBuilder s = new StringBuilder();
		for (String key : queryParameters.keySet()) {
			appendQuery(s
		}
		return s.toString();
	}

	private static void appendQuery(StringBuilder s
			String value) {
		if (s.length() != 0) {
		}
	}

	static Map<String
			S3Config bucketConfig
			Map<String
		addHostHeader(url

		Date now = new Date();
		String dateTimeStamp = dateTimeStampISO8601(now);
		headers.put(X_AMZ_DATE

		String canonicalizedHeaderNames = canonicalizeHeaderNames(headers);
		String canonicalizedHeaders = canonicalizeHeaderString(headers);
		String canonicalRequest = canonicalRequest(url
				canonicalizedHeaderNames
		String dateStamp = dateStamp(now);
		String scope = scope(bucketConfig.getRegion()

		byte[] signature = createSignature(bucketConfig
				dateStamp

		headers.put(HDR_AUTHORIZATION
				canonicalizedHeaderNames

		return headers;
	}

	private static String formatAuthorizationHeader(
			S3Config bucketConfig
			String scope
		StringBuilder s = new StringBuilder();
				.append(scope).append("
		return s.toString();
	}

	private static void addHostHeader(URL url
			Map<String
		StringBuilder hostHeader = new StringBuilder(url.getHost());
		int port = url.getPort();
		if (port > -1) {
		}
		headers.put("Host"
	}

	private static String canonicalizeHeaderNames(
			Map<String
		List<String> sortedHeaders = new ArrayList<>();
		sortedHeaders.addAll(headers.keySet());
		Collections.sort(sortedHeaders

		StringBuilder buffer = new StringBuilder();
		for (String header : sortedHeaders) {
			if (buffer.length() > 0)
			buffer.append(header.toLowerCase(Locale.ROOT));
		}

		return buffer.toString();
	}

	private static String canonicalizeHeaderString(
			Map<String
		if (headers == null || headers.isEmpty()) {
		}

		List<String> sortedHeaders = new ArrayList<>();
		sortedHeaders.addAll(headers.keySet());
		Collections.sort(sortedHeaders

		StringBuilder buffer = new StringBuilder();
		for (String key : sortedHeaders) {
			buffer.append(
					key.toLowerCase(Locale.ROOT).replaceAll("\\s+"
					+ headers.get(key).replaceAll("\\s+"
		}

		return buffer.toString();
	}

	private static String dateStamp(Date now) {
		SimpleDateFormat dateStampFormat = new SimpleDateFormat(
				DATE_STRING_FORMAT);
		dateStampFormat.setTimeZone(new SimpleTimeZone(0
		String dateStamp = dateStampFormat.format(now);
		return dateStamp;
	}

	private static String dateTimeStampISO8601(Date now) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
				ISO8601_BASIC_FORMAT);
		dateTimeFormat.setTimeZone(new SimpleTimeZone(0
		String dateTimeStamp = dateTimeFormat.format(now);
		return dateTimeStamp;
	}

	private static String scope(String region
		String scope = String.format("%s/%s/%s/%s"
				TERMINATOR);
		return scope;
	}

	private static String canonicalizeQueryString(
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

	private static String canonicalRequest(URL endpoint
			String queryParameters
			String canonicalizedHeaders
		return String.format("%s\n%s\n%s\n%s\n%s\n%s"
				httpMethod
				queryParameters
				bodyHash);
	}

	private static String canonicalizeResourcePath(URL endpoint) {
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
		md.update(s.getBytes(UTF_8));
		return md.digest();
	}

	private static byte[] sign(String stringData
		try {
			byte[] data = stringData.getBytes(UTF_8);
			Mac mac = Mac.getInstance(HMACSHA256);
			mac.init(new SecretKeySpec(key
			return mac.doFinal(data);
		} catch (Exception e) {
			throw new RuntimeException(MessageFormat.format(
					LfsServerText.get().failedToCalcSignature
					e);
		}
	}

	private static String stringToSign(String scheme
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
			throw new RuntimeException(LfsServerText.get().unsupportedUtf8
		}
		if (keepPathSlash) {
			encoded = encoded.replace("%2F"
		}
		return encoded;
	}

	private static byte[] createSignature(S3Config bucketConfig
			String dateTimeStamp
			String scope
		String stringToSign = stringToSign(SCHEME
				scope

		byte[] signature = (SCHEME + bucketConfig.getSecretKey())
				.getBytes(UTF_8);
		signature = sign(dateStamp
		signature = sign(bucketConfig.getRegion()
		signature = sign(S3
		signature = sign(TERMINATOR
		signature = sign(stringToSign
		return signature;
	}
}
