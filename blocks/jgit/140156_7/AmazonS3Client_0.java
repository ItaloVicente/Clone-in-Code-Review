		if (null == op) {
			throw die(MessageFormat.format(CLIText.get().unsupportedOperation
					op));
		} else
			switch (op) {
				final URLConnection c = s3.get(bucket
				int len = c.getContentLength();
				try (InputStream in = c.getInputStream()) {
					outw.flush();
					final byte[] tmp = new byte[2048];
					while (len > 0) {
						final int n = in.read(tmp);
						if (n < 0)
							throw new EOFException(MessageFormat.format(
									CLIText.get().expectedNumberOfbytes
									valueOf(len)));
						outs.write(tmp
						len -= n;
					}
					outs.flush();
