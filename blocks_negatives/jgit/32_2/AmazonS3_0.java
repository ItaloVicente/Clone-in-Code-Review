	/**
	 * Simple command line interface to {@link AmazonS3}.
	 *
	 * @param argv
	 *            command line arguments. See usage for details.
	 * @throws IOException
	 *             an error occurred.
	 */
	public static void main(final String[] argv) throws IOException {
		if (argv.length != 4) {
			commandLineUsage();
			return;
		}

		AwtAuthenticator.install();
		HttpSupport.configureHttpProxy();

		final AmazonS3 s3 = new AmazonS3(properties(new File(argv[0])));
		final String op = argv[1];
		final String bucket = argv[2];
		final String key = argv[3];
		if ("get".equals(op)) {
			final URLConnection c = s3.get(bucket, key);
			int len = c.getContentLength();
			final InputStream in = c.getInputStream();
			try {
				final byte[] tmp = new byte[2048];
				while (len > 0) {
					final int n = in.read(tmp);
					if (n < 0)
						throw new EOFException("Expected " + len + " bytes.");
					System.out.write(tmp, 0, n);
					len -= n;
				}
			} finally {
				in.close();
			}
		} else if ("ls".equals(op) || "list".equals(op)) {
			for (final String k : s3.list(bucket, key))
				System.out.println(k);
		} else if ("rm".equals(op) || "delete".equals(op)) {
			s3.delete(bucket, key);
		} else if ("put".equals(op)) {
			final OutputStream os = s3.beginPut(bucket, key, null, null);
			final byte[] tmp = new byte[2048];
			int n;
			while ((n = System.in.read(tmp)) > 0)
				os.write(tmp, 0, n);
			os.close();
		} else {
			commandLineUsage();
		}
	}

	private static void commandLineUsage() {
		System.err.println("usage: conn.prop op bucket key");
		System.err.println();
		System.err.println("    where conn.prop is a jets3t properties file.");
		System.err.println("    op is one of: get ls rm put");
		System.err.println("    bucket is the name of the S3 bucket");
		System.err.println("    key is the name of the object.");
		System.exit(1);
	}

