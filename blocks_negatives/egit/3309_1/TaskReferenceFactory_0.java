		try {
			String ipAddress1 = InetAddress.getByName(hostname1).getHostAddress();
			String ipAddress2 = InetAddress.getByName(hostname2).getHostAddress();

			return ipAddress1.equals(ipAddress2);
		} catch (UnknownHostException ex) {
		}

