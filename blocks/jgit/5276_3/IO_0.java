			byte[] buf = new byte[(int) Math.min(path.length()
			int valid = 0;
			for (;;) {
				if (buf.length == valid) {
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
