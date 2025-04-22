    ProxyHTTP buildProxy(final JGitFileSystemProviderConfiguration config) {
        final String host;
        final int port;
        String user = null;
        String passw = null;
        if (config.isSshOverHttpProxy()) {
            host = config.getHttpProxyHost();
            port = config.getHttpProxyPort();
            user = config.getHttpProxyUser();
            passw = config.getHttpProxyPassword();
        } else {
            host = config.getHttpsProxyHost();
            port = config.getHttpsProxyPort();
            user = config.getHttpsProxyUser();
            passw = config.getHttpsProxyPassword();
        }
        final ProxyHTTP proxyHTTP = new ProxyHTTP(host, port);
        if (user != null) {
            proxyHTTP.setUserPasswd(user, passw);
        }
        return proxyHTTP;
    }
