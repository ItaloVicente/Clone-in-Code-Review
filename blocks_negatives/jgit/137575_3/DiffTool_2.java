	private void showStream(ObjectStream stream)
			throws UnsupportedEncodingException, IOException {
		int read = 0;
		byte[] bytes = new byte[1024];
		while ((read = stream.read(bytes)) != -1) {
			outw.write(new String(bytes, "UTF-8").toCharArray(), 0, read); //$NON-NLS-1$
		}
	}
