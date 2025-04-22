package com.couchbase.client.core.security.sasl;

import javax.security.auth.callback.CallbackHandler;
import javax.security.sasl.SaslClient;
import javax.security.sasl.SaslClientFactory;
import javax.security.sasl.SaslException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class ShaSaslClientFactory implements SaslClientFactory {
    private static final String[] supportedMechanisms =
            new String[]{"SCRAM-SHA512", "SCRAM-SHA256", "SCRAM-SHA1"};

    @Override
    public SaslClient createSaslClient(String[] mechanisms, String authorizationId, String protocol, String serverName,
        Map<String, ?> props, CallbackHandler cbh) throws SaslException {

        int sha = 0;

        for (String m : mechanisms) {
            if (m.equals("SCRAM-SHA512")) {
                sha = 512;
            } else if (m.equals("SCRAM-SHA256")) {
                sha = 256;
            } else if (m.equals("SCRAM-SHA1")) {
                sha = 1;
            }
        }

        if (sha == 0) {
            return null;
        }

        if (authorizationId != null) {
            throw new SaslException("authorizationId is not supported (yet)");
        }

        if (cbh == null) {
            throw new SaslException("Callback handler must be set");
        }


        try {
            return new ShaSaslClient(cbh, sha);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    @Override
    public String[] getMechanismNames(Map<String, ?> props) {
        return supportedMechanisms;
    }
}
