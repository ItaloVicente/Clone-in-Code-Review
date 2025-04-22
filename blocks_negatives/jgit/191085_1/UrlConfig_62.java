/*
 * Copyright (C) 2022, Workday Inc.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Distribution License v. 1.0 which is available at
 *
 * SPDX-License-Identifier: BSD-3-Clause
 */

package org.eclipse.jgit.transport;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.util.Hex;
import org.eclipse.jgit.util.HttpSupport;

/**
 * Utility class for signing requests to AWS service endpoints using the V4
 * signing protocol.
 *
 * Reference implementation: <a href=
 *
 * @see <a href=
 *      Signature Version 4</a>
 *
 * @since 5.13.1
 */
public final class AwsRequestSignerV4 {

	/** AWS version 4 signing algorithm (for authorization header). **/

	/** Java Message Authentication Code (MAC) algorithm name. **/

	/** AWS version 4 signing scheme. **/

	/** AWS version 4 terminator string. **/

	/** SHA-256 hash of an empty request body. **/

	/** Date format for the 'x-amz-date' header. **/
	private static final DateTimeFormatter AMZ_DATE_FORMAT = DateTimeFormatter

	/** Date format for the string-to-sign's scope. **/
	private static final DateTimeFormatter SCOPE_DATE_FORMAT = DateTimeFormatter

	private AwsRequestSignerV4() {
	}

	/**
	 * Sign the provided request with an AWS4 signature as the 'Authorization'
	 * header.
	 *
	 * @param httpURLConnection
	 *            The request to sign.
	 * @param queryParameters
	 *            The query parameters being sent in the request.
	 * @param contentLength
	 *            The content length of the data being sent in the request
	 * @param bodyHash
	 *            Hex-encoded SHA-256 hash of the data being sent in the request
	 * @param serviceName
	 *            The signing name of the AWS service (e.g. "s3").
	 * @param regionName
	 *            The name of the AWS region that will handle the request (e.g.
	 *            "us-east-1").
	 * @param awsAccessKey
	 *            The user's AWS Access Key.
	 * @param awsSecretKey
	 *            The user's AWS Secret Key.
	 */
	public static void sign(HttpURLConnection httpURLConnection,
			Map<String, String> queryParameters, long contentLength,
			String bodyHash, String serviceName, String regionName,
			String awsAccessKey, char[] awsSecretKey) {
		Map<String, String> headers = new HashMap<>();
		httpURLConnection.getRequestProperties()
				.forEach((headerName, headerValues) -> headers.put(headerName,
						String.join(",, headerValues))); //$NON-NLS-1$
-
-		// add required content headers
-		if (contentLength > 0) {
-			headers.put(HttpSupport.HDR_CONTENT_LENGTH,
-					String.valueOf(contentLength));
-		} else {
-			bodyHash = EMPTY_BODY_SHA256;
-		}
-		headers.put(x-amz-content-sha256", bodyHash); //$NON-NLS-1$

		OffsetDateTime now = Instant.now().atOffset(ZoneOffset.UTC);
		String amzDate = now.format(AMZ_DATE_FORMAT);
		headers.put("x-amz-date", amzDate); //$NON-NLS-1$

		URL endpointUrl = httpURLConnection.getURL();
		int port = endpointUrl.getPort();
		String hostHeader = (port > -1)
				: endpointUrl.getHost();
		headers.put("Host", hostHeader); //$NON-NLS-1$

		String canonicalizedHeaderNames = getCanonicalizeHeaderNames(headers);
		String canonicalizedHeaders = getCanonicalizedHeaderString(headers);
		String canonicalizedQueryParameters = getCanonicalizedQueryString(
				queryParameters);
		String httpMethod = httpURLConnection.getRequestMethod();
		String canonicalRequest = httpMethod + '\n'
				+ getCanonicalizedResourcePath(endpointUrl) + '\n'
				+ canonicalizedQueryParameters + '\n' + canonicalizedHeaders
				+ '\n' + canonicalizedHeaderNames + '\n' + bodyHash;

		String scopeDate = now.format(SCOPE_DATE_FORMAT);
		String scope = scopeDate + '/' + regionName + '/' + serviceName + '/'
				+ TERMINATOR;
		String stringToSign = SCHEME + '-' + ALGORITHM + '\n' + amzDate + '\n'
				+ scope + '\n' + Hex.toHexString(hash(
						canonicalRequest.getBytes(StandardCharsets.UTF_8)));

		byte[] secretKey = (SCHEME + new String(awsSecretKey)).getBytes();
		byte[] dateKey = signStringWithKey(scopeDate, secretKey);
		byte[] regionKey = signStringWithKey(regionName, dateKey);
		byte[] serviceKey = signStringWithKey(serviceName, regionKey);
		byte[] signingKey = signStringWithKey(TERMINATOR, serviceKey);
		byte[] signature = signStringWithKey(stringToSign, signingKey);

				+ '/' + scope;
				+ canonicalizedHeaderNames;
				+ Hex.toHexString(signature);
		String authorizationHeader = SCHEME + '-' + ALGORITHM + ' '
				+ credentialsAuthorizationHeader + ", " //$NON-NLS-1$
				+ signedHeadersAuthorizationHeader + ", " //$NON-NLS-1$
				+ signatureAuthorizationHeader;

		headers.forEach(httpURLConnection::setRequestProperty);

		httpURLConnection.setRequestProperty(HttpSupport.HDR_AUTHORIZATION,
				authorizationHeader);
	}

	/**
	 * Calculates the hex-encoded SHA-256 hash of the provided byte array.
	 *
	 * @param data
	 *            Byte array to hash
	 *
	 * @return Hex-encoded SHA-256 hash of the provided byte array.
	 */
	public static String calculateBodyHash(final byte[] data) {
		return (data == null || data.length < 1) ? EMPTY_BODY_SHA256
				: Hex.toHexString(hash(data));
	}

	/**
	 * Construct a string listing all request headers in sorted case-insensitive
	 * order, separated by a ';'.
	 *
	 * @param headers
	 *            Map containing all request headers.
	 *
	 * @return String that lists all request headers in sorted case-insensitive
	 *         order, separated by a ';'.
	 */
	private static String getCanonicalizeHeaderNames(
			Map<String, String> headers) {
		return headers.keySet().stream().map(String::toLowerCase).sorted()
	}

	/**
	 * Constructs the canonical header string for a request.
	 *
	 * @param headers
	 *            Map containing all request headers.
	 *
	 * @return The canonical headers with values for the request.
	 */
	private static String getCanonicalizedHeaderString(
			Map<String, String> headers) {
		if (headers == null || headers.isEmpty()) {
		}
		StringBuilder sb = new StringBuilder();
		headers.keySet().stream().sorted(String.CASE_INSENSITIVE_ORDER)
				.forEach(key -> {
					String header = key.toLowerCase().replaceAll("\\s+", " "); //$NON-NLS-1$ //$NON-NLS-2$
					String value = headers.get(key).replaceAll("\\s+", " "); //$NON-NLS-1$ //$NON-NLS-2$
					sb.append(header).append(':').append(value).append('\n');
				});
		return sb.toString();
	}

	/**
	 * Constructs the canonicalized resource path for an AWS service endpoint.
	 *
	 * @param url
	 *            The AWS service endpoint URL, including the path to any
	 *            resource.
	 *
	 * @return The canonicalized resource path for the AWS service endpoint.
	 */
	private static String getCanonicalizedResourcePath(URL url) {
		if (url == null) {
		}
		String path = url.getPath();
		if (path == null || path.isEmpty()) {
		}
		String encodedPath = HttpSupport.urlEncode(path, true);
			return encodedPath;
		}
	}

	/**
	 * Constructs the canonicalized query string for a request.
	 *
	 * @param queryParameters
	 *            The query parameters in the request.
	 *
	 * @return The canonicalized query string for the request.
	 */
	public static String getCanonicalizedQueryString(
			Map<String, String> queryParameters) {
		if (queryParameters == null || queryParameters.isEmpty()) {
		}
		return queryParameters
				.keySet().stream().sorted().map(
						key -> HttpSupport.urlEncode(key, false) + '='
								+ HttpSupport.urlEncode(
										queryParameters.get(key), false))
	}

	/**
	 * Hashes the provided byte array using the SHA-256 algorithm.
	 *
	 * @param data
	 *            The byte array to hash.
	 *
	 * @return Hashed string contents of the provided byte array.
	 */
	public static byte[] hash(byte[] data) {
		try {
			md.update(data);
			return md.digest();
		} catch (Exception e) {
			throw new RuntimeException(
					JGitText.get().couldNotHashByteArrayWithSha256, e);
		}
	}

	/**
	 * Signs the provided string data using the specified key.
	 *
	 * @param stringToSign
	 *            The string data to sign.
	 * @param key
	 *            The key material of the secret key.
	 *
	 * @return Signed string data.
	 */
	private static byte[] signStringWithKey(String stringToSign, byte[] key) {
		try {
			byte[] data = stringToSign.getBytes(StandardCharsets.UTF_8);
			Mac mac = Mac.getInstance(MAC_ALGORITHM);
			mac.init(new SecretKeySpec(key, MAC_ALGORITHM));
			return mac.doFinal(data);
		} catch (Exception e) {
			throw new RuntimeException(JGitText.get().couldNotSignStringWithKey,
					e);
		}
	}

}
