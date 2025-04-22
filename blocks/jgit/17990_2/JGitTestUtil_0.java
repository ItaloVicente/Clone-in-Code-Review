		if ("jar".equals(url.getProtocol())) {
			try {
				File tmp = File.createTempFile("tmp_"
				copyTestResource(fileName
				return tmp;
			} catch (IOException err) {
				throw new RuntimeException("Cannot create temporary file"
			}
		}
