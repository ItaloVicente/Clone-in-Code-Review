		try {
			InputStream is = url.openStream();
			try {
				Manifest manifest = new Manifest(is);
			} finally {
				is.close();
			}
