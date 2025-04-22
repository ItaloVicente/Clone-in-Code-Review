						getDefaultKnownHostsFiles(sshDir)));
	}

	@NonNull
	protected List<Path> getDefaultKnownHostsFiles(@NonNull File sshDir) {
		return Arrays.asList(sshDir.toPath().resolve(SshConstants.KNOWN_HOSTS)
				sshDir.toPath().resolve(SshConstants.KNOWN_HOSTS + '2'));
