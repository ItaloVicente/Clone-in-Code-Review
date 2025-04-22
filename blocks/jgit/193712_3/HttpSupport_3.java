
package org.eclipse.jgit.transport;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.stream.Collectors;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.util.Hex;
import org.eclipse.jgit.util.HttpSupport;

public final class AwsRequestSignerV4 {






	private static final SimpleDateFormat AMZ_DATE_FORMAT;

	private static final SimpleDateFormat SCOPE_DATE_FORMAT;

	private static final DateTimeFormatter AMZ_DATE_FORMAT_NEW = DateTimeFormatter

	private static final DateTimeFormatter SCOPE_DATE_FORMAT_NEW = DateTimeFormatter

	static {
		AMZ_DATE_FORMAT.setTimeZone(new SimpleTimeZone(0
		SCOPE_DATE_FORMAT.setTimeZone(new SimpleTimeZone(0
	}

	private AwsRequestSignerV4() {
	}

	public static void sign(HttpURLConnection httpURLConnection
			Map<String
			String bodyHash
			String awsAccessKey
		Map<String
		httpURLConnection.getRequestProperties()
				.forEach((headerName
						String.join("

		OffsetDateTime now = Instant.now().atOffset(ZoneOffset.UTC);
		String amzDate = now.format(AMZ_DATE_FORMAT_NEW);
		headers.put("x-amz-date"

		URL endpointUrl = httpURLConnection.getURL();
		int port = endpointUrl.getPort();
		String hostHeader = (port > -1)
				: endpointUrl.getHost();
		headers.put("Host"

		String canonicalizedHeaderNames = getCanonicalizeHeaderNames(headers);
		String canonicalizedHeaders = getCanonicalizedHeaderString(headers);
		String canonicalizedQueryParameters = getCanonicalizedQueryString(
				queryParameters);
		String httpMethod = httpURLConnection.getRequestMethod();
		String canonicalRequest = httpMethod + '\n'
				+ getCanonicalizedResourcePath(endpointUrl) + '\n'
				+ canonicalizedQueryParameters + '\n' + canonicalizedHeaders
				+ '\n' + canonicalizedHeaderNames + '\n' + bodyHash;

		String scopeDate = now.format(SCOPE_DATE_FORMAT_NEW);
		String scope = scopeDate + '/' + regionName + '/' + serviceName + '/'
				+ TERMINATOR;
		String stringToSign = SCHEME + '-' + ALGORITHM + '\n' + amzDate + '\n'
				+ scope + '\n' + Hex.toHexString(hash(
						canonicalRequest.getBytes(StandardCharsets.UTF_8)));

		byte[] secretKey = (SCHEME + new String(awsSecretKey)).getBytes();
		byte[] dateKey = signStringWithKey(scopeDate
		byte[] regionKey = signStringWithKey(regionName
		byte[] serviceKey = signStringWithKey(serviceName
		byte[] signingKey = signStringWithKey(TERMINATOR
		byte[] signature = signStringWithKey(stringToSign

				+ '/' + scope;
				+ canonicalizedHeaderNames;
				+ Hex.toHexString(signature);
		String authorizationHeader = SCHEME + '-' + ALGORITHM + ' '
				+ credentialsAuthorizationHeader + "
				+ signedHeadersAuthorizationHeader + "
				+ signatureAuthorizationHeader;

		headers.forEach(httpURLConnection::setRequestProperty);

		httpURLConnection.setRequestProperty(HttpSupport.HDR_AUTHORIZATION
				authorizationHeader);
	}

	public static String calculateBodyHash(final byte[] data) {
		return (data == null || data.length < 1) ? EMPTY_BODY_SHA256
				: Hex.toHexString(hash(data));
	}

	private static String getCanonicalizeHeaderNames(
			Map<String
		return headers.keySet().stream().map(String::toLowerCase).sorted()
	}

	private static String getCanonicalizedHeaderString(
			Map<String
		if (headers == null || headers.isEmpty()) {
		}
		StringBuilder sb = new StringBuilder();
		headers.keySet().stream().sorted(String.CASE_INSENSITIVE_ORDER)
				.forEach(key -> {
					String header = key.toLowerCase().replaceAll("\\s+"
					String value = headers.get(key).replaceAll("\\s+"
				});
		return sb.toString();
	}

	private static String getCanonicalizedResourcePath(URL url) {
		if (url == null) {
		}
		String path = url.getPath();
		if (path == null || path.isEmpty()) {
		}
		String encodedPath = HttpSupport.urlEncode(path
			return encodedPath;
		}
	}

	public static String getCanonicalizedQueryString(
			Map<String
		if (queryParameters == null || queryParameters.isEmpty()) {
		}
		return queryParameters
				.keySet().stream().sorted().map(
						key -> HttpSupport.urlEncode(key
								+ HttpSupport.urlEncode(
										queryParameters.get(key)
	}

	public static byte[] hash(byte[] data) {
		try {
			md.update(data);
			return md.digest();
		} catch (Exception e) {
			throw new RuntimeException(
					JGitText.get().couldNotHashByteArrayWithSha256
		}
	}

	private static byte[] signStringWithKey(String stringToSign
		try {
			byte[] data = stringToSign.getBytes(StandardCharsets.UTF_8);
			Mac mac = Mac.getInstance(MAC_ALGORITHM);
			mac.init(new SecretKeySpec(key
			return mac.doFinal(data);
		} catch (Exception e) {
			throw new RuntimeException(JGitText.get().couldNotSignStringWithKey
					e);
		}
	}

}
