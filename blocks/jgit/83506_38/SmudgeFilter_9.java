			byte[] buf = new byte[8192];
			while ((length = in.read(buf)) != -1) {
				out.write(buf
				totalRead += length;

				if (totalRead >= MAX_COPY_BYTES) {
					return totalRead;
				}
