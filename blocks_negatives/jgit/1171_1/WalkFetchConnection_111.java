		tmp = File.createTempFile("noz", null, local.getObjectsDirectory());
		try {
			final FileOutputStream out = new FileOutputStream(tmp);
			try {
				out.write(compressed);
			} finally {
				out.close();
			}
			tmp.setReadOnly();
		} catch (IOException e) {
			tmp.delete();
			throw e;
