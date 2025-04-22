			final InputStream in = openInputStream(c);
			try {
				BaseConnection f;
				if (isSmartHttp(c, service)) {
					readSmartHeaders(in, service);
					f = new SmartHttpFetchConnection(in);
				} else {
					f = newDumbConnection(in);
				}
				f.setPeerUserAgent(c.getHeaderField(HttpSupport.HDR_SERVER));
				return (FetchConnection) f;
			} finally {
				in.close();
