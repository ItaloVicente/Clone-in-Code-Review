	private String runSshCommand(URIish sshUri, CredentialsProvider provider,
			FS fs, String command) throws IOException {
		RemoteSession session = null;
		Process process = null;
		StreamCopyThread errorThread = null;
		try (MessageWriter stderr = new MessageWriter()) {
			session = SshSessionFactory.getInstance().getSession(sshUri,
					provider, fs, 1000 * timeout);
			process = session.exec(command, 0);
			errorThread = new StreamCopyThread(process.getErrorStream(),
					stderr.getRawStream());
			errorThread.start();
			try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(process.getInputStream(),
							Constants.CHARSET))) {
				return reader.readLine();
			}
		} finally {
			if (errorThread != null) {
				try {
					errorThread.halt();
				} catch (InterruptedException e) {
				} finally {
					errorThread = null;
				}
			}
			if (process != null) {
				process.destroy();
			}
			if (session != null) {
				SshSessionFactory.getInstance().releaseSession(session);
			}
		}
	}

