	private String md5(URL url) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length = 0;
		try {
			InputStream inputStream = url.openStream();
			while ((length = inputStream.read(buffer)) != -1) {
				baos.write(buffer, 0, length);
			}
			inputStream.close();
			byte[] digest = MessageDigest.getInstance("MD5").digest(baos.toByteArray()); //$NON-NLS-1$
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < digest.length; ++i) {
				sb.append(Integer.toHexString((digest[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (Exception e) {
			throw new Error(e);
		}

	}

