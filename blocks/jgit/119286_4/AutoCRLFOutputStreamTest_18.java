			try (InputStream in = new ByteArrayInputStream(inbytes);
					ByteArrayOutputStream bos = new ByteArrayOutputStream();
					OutputStream out = new AutoCRLFOutputStream(bos)) {
				if (i > 0) {
					int n;
					while ((n = in.read(buf)) >= 0) {
						out.write(buf
					}
				} else if (i < 0) {
					int n;
					while ((n = in.read(buf)) >= 0) {
						byte[] b = new byte[n];
						System.arraycopy(buf
						out.write(b);
					}
				} else {
					int c;
					while ((c = in.read()) != -1)
						out.write(c);
