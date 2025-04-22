package com.couchbase.client.core.security.sasl;

import javax.security.auth.callback.CallbackHandler;
import javax.security.sasl.SaslClient;
import javax.security.sasl.SaslException;
import java.util.Map;

public class Sasl {

    private static ShaSaslClientFactory SASL_FACTORY = new ShaSaslClientFactory();

    public static SaslClient createSaslClient(String[] mechanisms, String authorizationId, String protocol,
        String serverName, Map<String, ?> props, CallbackHandler cbh) throws SaslException {
        for (String mech : mechanisms) {
            String[] mechs = new String[] { mech };

            SaslClient client = javax.security.sasl.Sasl.createSaslClient(
                mechs, authorizationId, protocol, serverName, props, cbh
            );

            if (client == null) {
                client = SASL_FACTORY.createSaslClient(
                    mechs, authorizationId, protocol, serverName, props, cbh
                );
            }

            if (client != null) {
                return client;
            }
        }

        return null;
    }
}
