
			byte[] buf = new byte[(int) sz];
			int valid = 0;
			for (;;) {
				if (buf.length == valid) {
					if (buf.length == max) {
						int next = in.read();
						if (next < 0)
							break;

						throw new IOException(MessageFormat.format(
								JGitText.get().fileIsTooLarge
					}

					byte[] nb = new byte[buf.length * 2];
					System.arraycopy(buf
					buf = nb;
				}
				int n = in.read(buf
				if (n < 0)
					break;
				valid += n;
			}
			if (valid < buf.length) {
				byte[] nb = new byte[valid];
				System.arraycopy(buf
				buf = nb;
			}
