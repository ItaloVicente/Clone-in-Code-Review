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

/**
 * Signing Amazon S3 requests using Signature Version 4
 */
class S3V4Signer {










	private static String getCanonicalizeHeaderNames(
			Map<String, String> headers) {
		List<String> sortedHeaders = new ArrayList<String>();
		sortedHeaders.addAll(headers.keySet());
		Collections.sort(sortedHeaders, String.CASE_INSENSITIVE_ORDER);

		StringBuilder buffer = new StringBuilder();
		for (String header : sortedHeaders) {
			if (buffer.length() > 0)
			buffer.append(header.toLowerCase());
		}

		return buffer.toString();
	}

	private static String getCanonicalizedHeaderString(
			Map<String, String> headers) {
		if (headers == null || headers.isEmpty()) {
		}

		List<String> sortedHeaders = new ArrayList<String>();
		sortedHeaders.addAll(headers.keySet());
		Collections.sort(sortedHeaders, String.CASE_INSENSITIVE_ORDER);

		StringBuilder buffer = new StringBuilder();
		for (String key : sortedHeaders) {
			buffer.append(key.toLowerCase().replaceAll("\\s+", " ") + ":" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
					+ headers.get(key).replaceAll("\\s+", " ")); //$NON-NLS-1$//$NON-NLS-2$
		}

		return buffer.toString();
	}

	private static String getCanonicalizedQueryString(
			Map<String, String> parameters) {
		if (parameters == null || parameters.isEmpty()) {
		}

		SortedMap<String, String> sorted = new TreeMap<String, String>();

		Iterator<Map.Entry<String, String>> pairs = parameters.entrySet()
				.iterator();
		while (pairs.hasNext()) {
			Map.Entry<String, String> pair = pairs.next();
			String key = pair.getKey();
			String value = pair.getValue();
			sorted.put(urlEncode(key, false), urlEncode(value, false));
		}

		StringBuilder builder = new StringBuilder();
		pairs = sorted.entrySet().iterator();
		while (pairs.hasNext()) {
			Map.Entry<String, String> pair = pairs.next();
			builder.append(pair.getKey());
			builder.append(pair.getValue());
			if (pairs.hasNext()) {
			}
		}

		return builder.toString();
	}

	private static String getCanonicalRequest(URL endpoint, String httpMethod,
			String queryParameters, String canonicalizedHeaderNames,
			String canonicalizedHeaders, String bodyHash) {
		return String.format("%s\n%s\n%s\n%s\n%s\n%s", //$NON-NLS-1$
				httpMethod, getCanonicalizedResourcePath(endpoint),
				queryParameters, canonicalizedHeaders, canonicalizedHeaderNames,
				bodyHash);
	}

	private static String getCanonicalizedResourcePath(URL endpoint) {
		if (endpoint == null) {
		}
		String path = endpoint.getPath();
		if (path == null || path.isEmpty()) {
		}

		String encodedPath = urlEncode(path, true);
			return encodedPath;
		} else {
		}
	}

	private static byte[] hash(String s) {
		MessageDigest md = Constants.newMessageDigest();
		md.update(s.getBytes(StandardCharsets.UTF_8));
		return md.digest();
	}

	private static byte[] sign(String stringData, byte[] key) {
		try {
			Mac mac = Mac.getInstance(HMACSHA256);
			mac.init(new SecretKeySpec(key, HMACSHA256));
			return mac.doFinal(data);
		} catch (Exception e) {
			throw new RuntimeException(
					"Unable to calculate a request signature: "
							+ e.getMessage(),
					e);
		}
	}


	private static String getStringToSign(String scheme, String algorithm,
			String dateTime, String scope, String canonicalRequest) {
		return String.format("%s-%s\n%s\n%s\n%s", //$NON-NLS-1$
				scheme, algorithm, dateTime, scope,
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

	private static String urlEncode(String url, boolean keepPathSlash) {
		String encoded;
		try {
			encoded = URLEncoder.encode(url, StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("UTF-8 encoding is not supported.", e);
		}
		if (keepPathSlash) {
			encoded = encoded.replace("%2F", "/"); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return encoded;
	}

	private final String accessKey;

	private final String secretKey;

	S3V4Signer(String accessKey, String secretKey) {
		this.accessKey = accessKey;
		this.secretKey = secretKey;
	}

	String presignGetRequestUrl(String region, String bucket, String path,
			int expirationSeconds) {

		URL endpointUrl;
		try {
			endpointUrl = new URL(String.format(
		} catch (MalformedURLException e) {
			throw new RuntimeException(
					"Unable to parse service endpoint: " + e.getMessage());
		}

		Map<String, String> queryParams = new HashMap<String, String>();
		queryParams.put("X-Amz-Expires", Integer.toString(expirationSeconds)); //$NON-NLS-1$

		Map<String, String> headers = new HashMap<String, String>();

		String authorizationQueryParameters = computeSignature(endpointUrl,
				"GET", //$NON-NLS-1$
				region, headers, queryParams, UNSIGNED_PAYLOAD);

	}

	/**
	 * Computes an AWS4 authorization for a request, suitable for embedding in
	 * query parameters.
	 *
	 * @param objectUrl
	 * @param httpMethod
	 * @param region
	 *
	 * @param headers
	 *            The request headers; 'Host' and 'X-Amz-Date' will be added to
	 *            this set.
	 * @param queryParameters
	 *            Any query parameters that will be added to the endpoint. The
	 *            parameters should be specified in canonical format.
	 * @param bodyHash
	 *            Precomputed SHA256 hash of the request body content; this
	 *            value should also be set as the header 'X-Amz-Content-SHA256'
	 *            for non-streaming uploads.
	 * @return The computed authorization string for the request. This value
	 *         needs to be set as the header 'Authorization' on the subsequent
	 *         HTTP request.
	 */
	private String computeSignature(URL objectUrl, String httpMethod,
			String region, Map<String, String> headers,
			Map<String, String> queryParameters, String bodyHash) {

		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
				ISO8601_BASIC_FORMAT);
		dateTimeFormat.setTimeZone(new SimpleTimeZone(0, UTC));
		Date now = new Date();
		String dateTimeStamp = dateTimeFormat.format(now);

		StringBuilder h = new StringBuilder(objectUrl.getHost());
		int port = objectUrl.getPort();
		if (port > -1) {
		}
		headers.put("Host", h.toString()); //$NON-NLS-1$

		String canonicalizedHeaderNames = getCanonicalizeHeaderNames(headers);
		String canonicalizedHeaders = getCanonicalizedHeaderString(headers);

		SimpleDateFormat dateStampFormat = new SimpleDateFormat(
				DATE_STRING_FORMAT);
		dateStampFormat.setTimeZone(new SimpleTimeZone(0, UTC));
		String dateStamp = dateStampFormat.format(now);
		String scope = String.format("%s/%s/%s/%s", dateStamp, region, S3, //$NON-NLS-1$
				TERMINATOR);

		queryParameters.put("X-Amz-Algorithm", SCHEME + "-" + ALGORITHM); //$NON-NLS-1$ //$NON-NLS-2$
		queryParameters.put("X-Amz-Credential", accessKey + "/" + scope); //$NON-NLS-1$ //$NON-NLS-2$

		queryParameters.put("X-Amz-Date", dateTimeStamp); //$NON-NLS-1$
		queryParameters.put("X-Amz-SignedHeaders", canonicalizedHeaderNames); //$NON-NLS-1$

		String canonicalizedQueryParameters = getCanonicalizedQueryString(
				queryParameters);

		String canonicalRequest = getCanonicalRequest(objectUrl, httpMethod,
				canonicalizedQueryParameters, canonicalizedHeaderNames,
				canonicalizedHeaders, bodyHash);

		String stringToSign = getStringToSign(SCHEME, ALGORITHM, dateTimeStamp,
				scope, canonicalRequest);

		byte[] kSecret = (SCHEME + secretKey).getBytes();
		byte[] kDate = sign(dateStamp, kSecret);
		byte[] kRegion = sign(region, kDate);
		byte[] kService = sign(S3, kRegion);
		byte[] kSigning = sign(TERMINATOR, kService);
		byte[] signature = sign(stringToSign, kSigning);

		StringBuilder s = new StringBuilder();
		return s.toString();
	}
}
