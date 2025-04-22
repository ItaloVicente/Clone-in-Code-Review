		InputStream in = url.openStream();
		try {
			FileOutputStream out = new FileOutputStream(dest);
			try {
				byte[] buf = new byte[4096];
				for (int n; (n = in.read(buf)) > 0;)
					out.write(buf, 0, n);
			} finally {
				out.close();
