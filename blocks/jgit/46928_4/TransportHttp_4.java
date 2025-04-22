	private PushConnection smartPush(String service
			InputStream in) throws IOException
		readSmartHeaders(in
		SmartHttpPushConnection p = new SmartHttpPushConnection(in);
		p.setPeerUserAgent(c.getHeaderField(HttpSupport.HDR_SERVER));
		return p;
	}

