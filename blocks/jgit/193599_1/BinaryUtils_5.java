
package org.eclipse.jgit.transport.aws;

import java.net.URL;
import java.util.Date;
import java.util.Map;

public class AWS4SignerForAuthorizationHeader extends AWS4SignerBase {

    public AWS4SignerForAuthorizationHeader(URL endpointUrl
    		String serviceName
        super(endpointUrl
    }
    
    public String computeSignature(Map<String
                                   Map<String
                                   final byte[] data
                                   String awsAccessKey
                                   char[] awsSecretKey) {
        final int contentLength = data == null ? 0 : data.length;
        final String bodyHash;
        if (contentLength > 0) {
            final byte[] contentHash = AWS4SignerBase.hash(data);
            bodyHash = BinaryUtils.toHex(contentHash);
            headers.put("content-length"
        }
        else {
            bodyHash = AWS4SignerBase.EMPTY_BODY_SHA256;
        }
        headers.put("x-amz-content-sha256"

        Date now = new Date();
        String dateTimeStamp = dateTimeFormat.format(now);

        headers.put("x-amz-date"
        
        String hostHeader = endpointUrl.getHost();
        int port = endpointUrl.getPort();
        if ( port > -1 ) {
            hostHeader.concat(":" + Integer.toString(port));
        }
        headers.put("Host"
        
        String canonicalizedHeaderNames = getCanonicalizeHeaderNames(headers);
        String canonicalizedHeaders = getCanonicalizedHeaderString(headers);
        
        String canonicalizedQueryParameters = getCanonicalizedQueryString(queryParameters);
        
        String canonicalRequest = getCanonicalRequest(endpointUrl
                canonicalizedQueryParameters
                canonicalizedHeaders
        
        String dateStamp = dateStampFormat.format(now);
        String scope =  dateStamp + "/" + regionName + "/" + serviceName + "/" + TERMINATOR;
        String stringToSign = getStringToSign(SCHEME
        
        byte[] kSecret = (SCHEME + new String(awsSecretKey)).getBytes();
        byte[] kDate = sign(dateStamp
        byte[] kRegion = sign(regionName
        byte[] kService = sign(serviceName
        byte[] kSigning = sign(TERMINATOR
        byte[] signature = sign(stringToSign
        
        String credentialsAuthorizationHeader =
                "Credential=" + awsAccessKey + "/" + scope;
        String signedHeadersAuthorizationHeader =
                "SignedHeaders=" + canonicalizedHeaderNames;
        String signatureAuthorizationHeader =
                "Signature=" + BinaryUtils.toHex(signature);

        String authorizationHeader = SCHEME + "-" + ALGORITHM + " "
                + credentialsAuthorizationHeader + "
                + signedHeadersAuthorizationHeader + "
                + signatureAuthorizationHeader;

        return authorizationHeader;
    }
}
