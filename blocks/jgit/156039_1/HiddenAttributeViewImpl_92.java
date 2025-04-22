
package org.eclipse.jgit.niofs.internal;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

public class HTTPProxyAuthenticator extends Authenticator {

    private final String httpProxyUser;
    private final String httpProxyPassword;
    private final String httpsProxyUser;
    private final String httpsProxyPassword;

    public HTTPProxyAuthenticator(final String httpProxyUser
                                  final String httpProxyPassword
                                  final String httpsProxyUser
                                  final String httpsProxyPassword) {
        this.httpProxyUser = httpProxyUser;
        this.httpProxyPassword = httpProxyPassword;
        this.httpsProxyUser = httpsProxyUser;
        this.httpsProxyPassword = httpsProxyPassword;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        if (getRequestorType() == RequestorType.PROXY) {
            final String protocol = getRequestingProtocol();

            if (protocol.equalsIgnoreCase("http") && (httpProxyUser != null && httpProxyPassword != null)) {
                return new PasswordAuthentication(httpProxyUser
                                                  httpProxyPassword.toCharArray());
            } else if (protocol.equalsIgnoreCase("https") && (httpsProxyUser != null && httpsProxyPassword != null)) {
                return new PasswordAuthentication(httpsProxyUser
                                                  httpsProxyPassword.toCharArray());
            }
        }
        return super.getPasswordAuthentication();
    }
}
