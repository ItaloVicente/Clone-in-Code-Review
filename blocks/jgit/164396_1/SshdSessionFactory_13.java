
	@NonNull
	protected ServerKeyDatabase createServerKeyDatabase(@NonNull File homeDir
			@NonNull File sshDir) {
		return new OpenSshServerKeyDatabase(true
				getDefaultKnownHostsFiles(sshDir));
	}

