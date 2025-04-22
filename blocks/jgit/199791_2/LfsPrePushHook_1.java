
	private void verifyFile(Protocol.ObjectInfo o
			Protocol.Action verifyAction) throws IOException {
		HttpConnection connection = LfsConnectionFactory
				.getLfsContentConnection(getRepository()
						METHOD_POST);
		connection.setRequestProperty(HDR_CONTENT_TYPE
				CONTENTTYPE_VND_GIT_LFS_JSON);
		connection.setDoOutput(true);
		try (OutputStream out = connection.getOutputStream()) {
			Protocol.ObjectSpec spec = new Protocol.ObjectSpec();
			spec.oid = o.oid;
			spec.size = o.size;
			out.write((Protocol.gson().toJson(spec)).getBytes(UTF_8));
		}
		int responseCode = connection.getResponseCode();
		if (responseCode != HTTP_OK) {
			throw new IOException(MessageFormat.format(
					LfsText.get().serverFailure
					Integer.valueOf(responseCode)));
		}
	}
