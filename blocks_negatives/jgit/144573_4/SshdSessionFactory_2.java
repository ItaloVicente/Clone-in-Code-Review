	 * Obtain a {@link ServerKeyVerifier} to read known_hosts files and to
	 * verify server host keys. The default implementation returns a
	 * {@link ServerKeyVerifier} that recognizes the two openssh standard files
	 * {@code ~/.ssh/known_hosts} and {@code ~/.ssh/known_hosts2} as well as any
	 * files configured via the {@code UserKnownHostsFile} option in the ssh
	 * config file.
