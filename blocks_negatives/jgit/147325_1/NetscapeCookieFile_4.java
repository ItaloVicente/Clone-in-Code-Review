	/**
	 * Writes all the cookies being maintained in the set being returned by
	 * {@link #getCookies(boolean)} to the underlying file.
	 *
	 * Session-cookies will not be persisted.
	 *
	 * @param url
	 *            url for which to write the cookies (important to derive
	 *            default values for non-explicitly set attributes)
	 * @throws IOException
	 * @throws IllegalArgumentException
	 * @throws InterruptedException
	 */
	public void write(URL url)
			throws IllegalArgumentException, IOException, InterruptedException {
		try {
			byte[] cookieFileContent = getFileContentIfModified();
			if (cookieFileContent != null) {
				LOG.debug(
						"Reading the underlying cookie file '{}' as it has been modified since the last access", //$NON-NLS-1$
						path);
				Set<HttpCookie> cookiesFromFile = NetscapeCookieFile
						.parseCookieFile(cookieFileContent, creationDate);
				this.cookies = mergeCookies(cookiesFromFile, cookies);
			}
		} catch (FileNotFoundException e) {
		}

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		try (Writer writer = new OutputStreamWriter(output,
				StandardCharsets.US_ASCII)) {
			write(writer, cookies, url, creationDate);
		}
		LockFile lockFile = new LockFile(path.toFile());
		for (int retryCount = 0; retryCount < LOCK_ACQUIRE_MAX_RETRY_COUNT; retryCount++) {
			if (lockFile.lock()) {
				try {
					lockFile.setNeedSnapshot(true);
					lockFile.write(output.toByteArray());
					if (!lockFile.commit()) {
						throw new IOException(MessageFormat.format(
								JGitText.get().cannotCommitWriteTo, path));
					}
				} finally {
					lockFile.unlock();
				}
				return;
			}
			Thread.sleep(LOCK_ACQUIRE_RETRY_SLEEP);
		}
		throw new IOException(
				MessageFormat.format(JGitText.get().cannotLock, lockFile));

	}

