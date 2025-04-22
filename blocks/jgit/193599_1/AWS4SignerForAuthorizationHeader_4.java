
package org.eclipse.jgit.transport.aws;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SimpleTimeZone;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public abstract class AWS4SignerBase {

    public static final String EMPTY_BODY_SHA256 = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";

    public static final String SCHEME = "AWS4";

    public static final String ALGORITHM = "HMAC-SHA256";

    public static final String TERMINATOR = "aws4_request";
    
    public static final String ISO8601_BASIC_FORMAT = "yyyyMMdd'T'HHmmss'Z'";

    public static final String DATE_STRING_FORMAT = "yyyyMMdd";

    protected URL endpointUrl;

    protected String httpMethod;

    protected String serviceName;

    protected String regionName;

    protected final SimpleDateFormat dateTimeFormat;

    protected final SimpleDateFormat dateStampFormat;
    
    public AWS4SignerBase(URL endpointUrl
            String serviceName
        this.endpointUrl = endpointUrl;
        this.httpMethod = httpMethod;
        this.serviceName = serviceName;
        this.regionName = regionName;
        
        dateTimeFormat = new SimpleDateFormat(ISO8601_BASIC_FORMAT);
        dateTimeFormat.setTimeZone(new SimpleTimeZone(0
        dateStampFormat = new SimpleDateFormat(DATE_STRING_FORMAT);
        dateStampFormat.setTimeZone(new SimpleTimeZone(0
    }
    
    protected static String getCanonicalizeHeaderNames(Map<String
        List<String> sortedHeaders = new ArrayList<String>();
        sortedHeaders.addAll(headers.keySet());
        Collections.sort(sortedHeaders

        StringBuilder buffer = new StringBuilder();
        for (String header : sortedHeaders) {
            if (buffer.length() > 0) buffer.append(";");
            buffer.append(header.toLowerCase());
        }

        return buffer.toString();
    }
    
    protected static String getCanonicalizedHeaderString(Map<String
        if ( headers == null || headers.isEmpty() ) {
            return "";
        }
        
        List<String> sortedHeaders = new ArrayList<String>();
        sortedHeaders.addAll(headers.keySet());
        Collections.sort(sortedHeaders

        StringBuilder buffer = new StringBuilder();
        for (String key : sortedHeaders) {
            buffer.append(key.toLowerCase().replaceAll("\\s+"
            buffer.append("\n");
        }

        return buffer.toString();
    }
    
    protected static String getCanonicalRequest(URL endpoint
            String queryParameters
            String canonicalizedHeaders
        String canonicalRequest =
            httpMethod + "\n" +
            getCanonicalizedResourcePath(endpoint) + "\n" +
            queryParameters + "\n" +
            canonicalizedHeaders + "\n" +
            canonicalizedHeaderNames + "\n" +
            bodyHash;
        return canonicalRequest;
    }
    
    protected static String getCanonicalizedResourcePath(URL endpoint) {
        if ( endpoint == null ) {
            return "/";
        }
        String path = endpoint.getPath();
        if ( path == null || path.isEmpty() ) {
            return "/";
        }
        
        String encodedPath = HttpUtils.urlEncode(path
        if (encodedPath.startsWith("/")) {
            return encodedPath;
        } else {
            return "/".concat(encodedPath);
        }
    }
    
    public static String getCanonicalizedQueryString(Map<String
        if ( parameters == null || parameters.isEmpty() ) {
            return "";
        }
        
        SortedMap<String

        Iterator<Map.Entry<String
        while (pairs.hasNext()) {
            Map.Entry<String
            String key = pair.getKey();
            String value = pair.getValue();
            sorted.put(HttpUtils.urlEncode(key
        }

        StringBuilder builder = new StringBuilder();
        pairs = sorted.entrySet().iterator();
        while (pairs.hasNext()) {
            Map.Entry<String
            builder.append(pair.getKey());
            builder.append("=");
            builder.append(pair.getValue());
            if (pairs.hasNext()) {
                builder.append("&");
            }
        }

        return builder.toString();
    }

    protected static String getStringToSign(String scheme
        String stringToSign =
            scheme + "-" + algorithm + "\n" +
            dateTime + "\n" +
            scope + "\n" +
            BinaryUtils.toHex(hash(canonicalRequest));
        return stringToSign;
    }

    public static byte[] hash(String text) {
        return hash(text.getBytes(StandardCharsets.UTF_8));
    }
    
    public static byte[] hash(byte[] data) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(data);
            return md.digest();
        } catch (Exception e) {
            throw new RuntimeException("Unable to compute hash while signing request: " + e.getMessage()
        }
    }

    protected static byte[] sign(String stringData
        try {
            byte[] data = stringData.getBytes("UTF-8");
            Mac mac = Mac.getInstance(algorithm);
            mac.init(new SecretKeySpec(key
            return mac.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("Unable to calculate a request signature: " + e.getMessage()
        }
    }
}
