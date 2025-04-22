			InputStream in = new ByteArrayInputStream(inbytes);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			OutputStream out = new AutoCRLFOutputStream(bos);
			if (i > 0) {
				int n;
				while ((n = in.read(buf)) >= 0) {
					out.write(buf, 0, n);
