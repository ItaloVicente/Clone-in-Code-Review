		final byte[] magic = new byte[14];
		if (!in.markSupported()) {
			throw new TransportException(uri
					JGitText.get().inputStreamMustSupportMark);
		}
		in.mark(14);
