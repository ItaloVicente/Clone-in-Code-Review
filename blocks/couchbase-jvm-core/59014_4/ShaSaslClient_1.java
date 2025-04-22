package com.couchbase.client.core.security.sasl;

import javax.security.auth.callback.CallbackHandler;
import javax.security.sasl.SaslClient;
import javax.security.sasl.SaslException;
import java.util.Map;

public class Sasl {

    public static SaslClient createSaslClient(String[] mechanisms, String authorizationId, String protocol,
        String serverName, Map<String, ?> props, CallbackHandler cbh) throws SaslException {
        for (String mech : mechanisms) {
            String[] mechs = new String[]{ mech };

            SaslClient ret = javax.security.sasl.Sasl.createSaslClient(mechs, authorizationId, protocol, serverName,
                props, cbh);

            if (ret == null) {
                ShaSaslClientFactory factory = new ShaSaslClientFactory();
                ret = factory.createSaslClient(mechs, authorizationId, protocol, serverName, props, cbh);
            }

            if (ret != null) {
                return ret;
            }
        }

        return null;
    }
}
