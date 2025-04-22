		config("Host \"good\"\n" + " HostName=\"good.tld\"\n"
				+ " Port=\"6007\"\n" + " User=\"gooduser\"\n"
				+ "Host multiple unquoted and \"quoted\" \"hosts\"\n"
				+ " Port=\"2222\"\n" + "Host \"spaced\"\n"
				+ "# Bad host name
				+ " HostName=\" spaced\ttld \"\n" + "# Misbalanced quotes\n"
				+ "Host \"bad\"\n" + "# OpenSSH doesn't allow this but ...\n"
				+ " HostName=bad.tld\"\n");
