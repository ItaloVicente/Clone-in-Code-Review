package com.couchbase.client.core.security.sasl;

import javax.security.auth.callback.CallbackHandler;
import javax.security.sasl.SaslClient;
import javax.security.sasl.SaslClientFactory;
import javax.security.sasl.SaslException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class ShaSaslClientFactory implements SaslClientFactory {

    private static final String SCRAM_SHA512 = "SCRAM-SHA512";
    private static final String SCRAM_SHA256 = "SCRAM-SHA256";
    private static final String SCRAM_SHA1 = "SCRAM-SHA1";
    private static final String[] SUPPORTED_MECHS = { SCRAM_SHA512, SCRAM_SHA256, SCRAM_SHA1 };

    @Override
    public SaslClient createSaslClient(String[] mechanisms, String authorizationId, String protocol,
        String serverName, Map<String, ?> props, CallbackHandler cbh) throws SaslException {

        int sha = 0;

        for (String m : mechanisms) {
            if (m.equals(SCRAM_SHA512)) {
                sha = 512;
                break;
            } else if (m.equals(SCRAM_SHA256)) {
                sha = 256;
                break;
            } else if (m.equals(SCRAM_SHA1)) {
                sha = 1;
                break;
            }
        }

        if (sha == 0) {
            return null;
        }

        if (authorizationId != null) {
            throw new SaslException("authorizationId is not supported");
        }

        if (cbh == null) {
            throw new SaslException("Callback handler to get username/password required");
        }

        try {
            return new ShaSaslClient(cbh, sha);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    @Override
    public String[] getMechanismNames(Map<String, ?> props) {
        return SUPPORTED_MECHS;
    }
}
