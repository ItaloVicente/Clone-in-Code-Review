		try {
			final InputStream in = new FileInputStream(propertyFile);
			try {
				final Properties p = new Properties();
				p.load(in);
				return p;
			} finally {
				in.close();
			}
