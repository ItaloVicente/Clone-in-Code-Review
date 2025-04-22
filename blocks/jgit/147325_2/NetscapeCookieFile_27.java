	public void write(URL url) throws IOException
		try {
			byte[] cookieFileContent = getFileContentIfModified();
			if (cookieFileContent != null) {
						+ "the last access"
						path);
				Set<HttpCookie> cookiesFromFile = NetscapeCookieFile
						.parseCookieFile(cookieFileContent
				this.cookies = mergeCookies(cookiesFromFile
			}
		} catch (FileNotFoundException e) {
		}

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try (Writer writer = new OutputStreamWriter(output
				StandardCharsets.US_ASCII)) {
			write(writer
		}
		LockFile lockFile = new LockFile(path.toFile());
		for (int retryCount = 0; retryCount < LOCK_ACQUIRE_MAX_RETRY_COUNT; retryCount++) {
			if (lockFile.lock()) {
				try {
					lockFile.setNeedSnapshot(true);
					lockFile.write(output.toByteArray());
					if (!lockFile.commit()) {
						throw new IOException(MessageFormat.format(
								JGitText.get().cannotCommitWriteTo
					}
				} finally {
					lockFile.unlock();
				}
				return;
			}
			Thread.sleep(LOCK_ACQUIRE_RETRY_SLEEP);
		}
		throw new IOException(
				MessageFormat.format(JGitText.get().cannotLock
	}

