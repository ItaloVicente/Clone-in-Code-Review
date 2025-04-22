            if (protocol.equalsIgnoreCase("http") && (httpProxyUser != null && httpProxyPassword != null)) {
                return new PasswordAuthentication(httpProxyUser,
                                                  httpProxyPassword.toCharArray());
            } else if (protocol.equalsIgnoreCase("https") && (httpsProxyUser != null && httpsProxyPassword != null)) {
                return new PasswordAuthentication(httpsProxyUser,
                                                  httpsProxyPassword.toCharArray());
            }
        }
        return super.getPasswordAuthentication();
    }
