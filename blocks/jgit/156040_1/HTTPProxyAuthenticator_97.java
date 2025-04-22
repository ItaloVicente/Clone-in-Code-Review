			if (protocol.equalsIgnoreCase("http") && (httpProxyUser != null && httpProxyPassword != null)) {
				return new PasswordAuthentication(httpProxyUser
			} else if (protocol.equalsIgnoreCase("https") && (httpsProxyUser != null && httpsProxyPassword != null)) {
				return new PasswordAuthentication(httpsProxyUser
			}
		}
		return super.getPasswordAuthentication();
	}
