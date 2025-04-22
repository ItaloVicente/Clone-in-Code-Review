
	private class URLCodedBasicAuthenticator extends Authenticator {
        public PasswordAuthentication getPasswordAuthentication () {
        	URL url = getRequestingURL();
        	String userInfo = url.getUserInfo();
			if (userInfo != null) {
				String[] info = userInfo.split(":"
				if (info.length == 2) {
					String user = info[0];
					String pass = info[1];
					return new PasswordAuthentication (user
				}
			}
			return super.getPasswordAuthentication();
        }
    }

