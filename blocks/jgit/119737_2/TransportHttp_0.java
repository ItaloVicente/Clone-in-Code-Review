	private FetchConnection getConnection(HttpConnection c
			String service) throws IOException {
		BaseConnection f;
		if (isSmartHttp(c
			readSmartHeaders(in
			f = new SmartHttpFetchConnection(in);
		} else {
			f = newDumbConnection(in);
		}
		f.setPeerUserAgent(c.getHeaderField(HttpSupport.HDR_SERVER));
		return (FetchConnection) f;
	}

