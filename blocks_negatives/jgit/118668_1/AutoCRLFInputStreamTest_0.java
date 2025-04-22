			ByteArrayInputStream bis = new ByteArrayInputStream(inbytes);
			InputStream in = new AutoCRLFInputStream(bis, true);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			if (i > 0) {
				int n;
				while ((n = in.read(buf)) >= 0) {
					out.write(buf, 0, n);
