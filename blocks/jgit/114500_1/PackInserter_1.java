			try {
				file.seek(0);
				out.write(hdrBuf

				byte[] buf = buffer();
				SHA1 md = digest().reset();
				file.seek(0);
				while (true) {
					int r = file.read(buf);
					if (r < 0) {
						break;
					}
					md.update(buf
