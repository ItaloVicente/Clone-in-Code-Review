			byte[] buf = new byte[(int) Math.min(sz
			int valid = 0;
			for (;;) {
				if (buf.length == valid) {
					int nsz = Math.min(max
					if (nsz == max)
						throw new IOException(MessageFormat.format(
								JGitText.get().fileIsTooLarge

					byte[] nb = new byte[nsz];
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
