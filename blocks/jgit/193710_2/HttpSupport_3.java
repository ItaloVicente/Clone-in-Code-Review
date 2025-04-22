
package org.eclipse.jgit.transport;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.stream.Collectors;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.eclipse.jgit.util.Hex;
import org.eclipse.jgit.util.HttpSupport;

public class AwsRequestSignerV4 {

    private static final String ALGORITHM = "HMAC-SHA256";

    private static final String MAC_ALGORITHM = "HmacSHA256";

    private static final String SCHEME = "AWS4";

    private static final String TERMINATOR = "aws4_request";

    private static final String EMPTY_BODY_SHA256 = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";

    private static final SimpleDateFormat AMZ_DATE_FORMAT;

    private static final SimpleDateFormat SCOPE_DATE_FORMAT;

    static {
        AMZ_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
        AMZ_DATE_FORMAT.setTimeZone(new SimpleTimeZone(0
        SCOPE_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
        SCOPE_DATE_FORMAT.setTimeZone(new SimpleTimeZone(0
    }

    private AwsRequestSignerV4() {
        throw new UnsupportedOperationException("Cannot instantiate helper class: " + getClass().getSimpleName());
    }

    public static void sign(HttpURLConnection httpURLConnection
            Map<String
            String regionName
        Map<String
        httpURLConnection.getRequestProperties().forEach((headerName
            headers.put(headerName
        }
        else {
            bodyHash = EMPTY_BODY_SHA256;
        }
        headers.put("x-amz-content-sha256"

        Date now = new Date();
        String amzDate = AMZ_DATE_FORMAT.format(now);
        headers.put("x-amz-date"

        URL endpointUrl = httpURLConnection.getURL();
        int port = endpointUrl.getPort();
        String hostHeader = (port > -1)
            ? endpointUrl.getHost().concat(":" + port)
            : endpointUrl.getHost();
        headers.put("Host"

        String canonicalizedHeaderNames = getCanonicalizeHeaderNames(headers);
        String canonicalizedHeaders = getCanonicalizedHeaderString(headers);
        String canonicalizedQueryParameters = getCanonicalizedQueryString(queryParameters);
        String httpMethod = httpURLConnection.getRequestMethod();
        String canonicalRequest = httpMethod + "\n" +
            getCanonicalizedResourcePath(endpointUrl) + "\n" +
            canonicalizedQueryParameters + "\n" +
            canonicalizedHeaders + "\n" +
            canonicalizedHeaderNames + "\n" +
            bodyHash;

        String scopeDate = SCOPE_DATE_FORMAT.format(now);
        String scope = scopeDate + "/" + regionName + "/" + serviceName + "/" + TERMINATOR;
        String stringToSign = SCHEME + "-" + ALGORITHM + "\n" +
            amzDate + "\n" +
            scope + "\n" +
            Hex.toHexString(hash(canonicalRequest.getBytes(StandardCharsets.UTF_8)));

        byte[] secretKey = (SCHEME + new String(awsSecretKey)).getBytes();
        byte[] dateKey = signStringWithKey(scopeDate
        byte[] regionKey = signStringWithKey(regionName
        byte[] serviceKey = signStringWithKey(serviceName
        byte[] signingKey = signStringWithKey(TERMINATOR
        byte[] signature = signStringWithKey(stringToSign

        String credentialsAuthorizationHeader = "Credential=" + awsAccessKey + "/" + scope;
        String signedHeadersAuthorizationHeader = "SignedHeaders=" + canonicalizedHeaderNames;
        String signatureAuthorizationHeader = "Signature=" + Hex.toHexString(signature);
        String authorizationHeader = SCHEME + "-" + ALGORITHM + " "
            + credentialsAuthorizationHeader + "
            + signedHeadersAuthorizationHeader + "
            + signatureAuthorizationHeader;

        headers.forEach(httpURLConnection::setRequestProperty);

        httpURLConnection.setRequestProperty("authorization"
    }

    private static String getCanonicalizeHeaderNames(Map<String
        return headers.keySet().stream()
            .map(String::toLowerCase)
            .sorted()
            .collect(Collectors.joining(";"));
    }

    private static String getCanonicalizedHeaderString(Map<String
        if (headers == null || headers.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        headers.keySet().stream()
            .sorted(String.CASE_INSENSITIVE_ORDER)
            .forEach(key -> {
                String header = key.toLowerCase().replaceAll("\\s+"
                String value = headers.get(key).replaceAll("\\s+"
                sb.append(header).append(":").append(value).append("\n");
            });
        return sb.toString();
    }

    private static String getCanonicalizedResourcePath(URL url) {
        if (url == null) {
            return "/";
        }
        String path = url.getPath();
        if (path == null || path.isEmpty()) {
            return "/";
        }
        String encodedPath = HttpSupport.urlEncode(path
        if (encodedPath.startsWith("/")) {
            return encodedPath;
        }
        else {
            return "/".concat(encodedPath);
        }
    }

    public static String getCanonicalizedQueryString(Map<String
        if (queryParameters == null || queryParameters.isEmpty()) {
            return "";
        }
        return queryParameters.keySet().stream()
            .sorted()
            .map(key ->
                HttpSupport.urlEncode(key
                + "="
                + HttpSupport.urlEncode(queryParameters.get(key)
            .collect(Collectors.joining("&"));
    }

    public static byte[] hash(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(data);
            return md.digest();
        }
        catch (Exception e) {
            throw new RuntimeException("Unable to hash byte array with SHA-256 algorithm: " + e.getMessage()
        }
    }

    private static byte[] signStringWithKey(String stringToSign
        try {
            byte[] data = stringToSign.getBytes(StandardCharsets.UTF_8);
            Mac mac = Mac.getInstance(MAC_ALGORITHM);
            mac.init(new SecretKeySpec(key
            return mac.doFinal(data);
        }
        catch (Exception e) {
            throw new RuntimeException("Unable to calculate a request signature: " + e.getMessage()
        }
    }

}
