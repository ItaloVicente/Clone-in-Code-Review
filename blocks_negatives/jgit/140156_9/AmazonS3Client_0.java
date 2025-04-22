			final URLConnection c = s3.get(bucket, key);
			int len = c.getContentLength();
			try (InputStream in = c.getInputStream()) {
				outw.flush();
				final byte[] tmp = new byte[2048];
				while (len > 0) {
					final int n = in.read(tmp);
					if (n < 0)
						throw new EOFException(MessageFormat.format(
								CLIText.get().expectedNumberOfbytes,
								valueOf(len)));
					outs.write(tmp, 0, n);
					len -= n;
				}
				outs.flush();
			}

			for (String k : s3.list(bucket, key))
				outw.println(k);

			s3.delete(bucket, key);

			try (OutputStream os = s3.beginPut(bucket, key, null, null)) {
				final byte[] tmp = new byte[2048];
				int n;
				while ((n = ins.read(tmp)) > 0)
					os.write(tmp, 0, n);
			}
		} else {
			throw die(MessageFormat.format(CLIText.get().unsupportedOperation, op));
		}
