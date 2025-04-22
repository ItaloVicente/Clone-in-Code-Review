			try (ByteArrayInputStream bis = new ByteArrayInputStream(inbytes);
					InputStream in = new AutoCRLFInputStream(bis
					ByteArrayOutputStream out = new ByteArrayOutputStream()) {
				if (i > 0) {
					int n;
					while ((n = in.read(buf)) >= 0) {
						out.write(buf
					}
				} else {
					int c;
					while ((c = in.read()) != -1)
						out.write(c);
