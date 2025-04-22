	private String translate(String uri)
			throws IOException
		UrlConfig urls = new UrlConfig(
				SystemReader.getInstance().getUserConfig());
		return urls.replace(uri);
	}
