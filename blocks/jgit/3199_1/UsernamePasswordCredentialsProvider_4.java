	public UsernamePasswordCredentialsProvider(String username
			String password
		this(username
				(certPassword == null) ? null : certPassword.toCharArray());
	}

	public UsernamePasswordCredentialsProvider(String username
			char[] password
		this.username = username;
		this.password = password;
		this.certPassword = certPassword;
	}

