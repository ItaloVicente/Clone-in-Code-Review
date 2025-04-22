		InputStream in = null;
		try {
			in = manager.getInputStream(path);
			if (in != null) {
				result = new Image(device, in);
			}
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				throw e;
			}
