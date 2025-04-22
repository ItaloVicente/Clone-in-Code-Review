		MessageDigest md = null;
		if (validate) {
			md = Constants.newMessageDigest();
			byte[] buf = out.getCopyBuffer();
			pin(pack, 0);
			if (window.copy(0, buf, 0, 12) != 12) {
				pack.setInvalid();
				throw new IOException(MessageFormat.format(
						JGitText.get().packfileIsTruncated, pack.getPackFile()
								.getPath()));
			}
			md.update(buf, 0, 12);
		}

