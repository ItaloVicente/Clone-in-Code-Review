		Map<String, Ref> readAdvertisedRefs() throws TransportException {
			try {
				final BufferedReader br = openReader(INFO_REFS);
				try {
					return readAdvertisedImpl(br);
				} finally {
					br.close();
				}
			} catch (IOException err) {
				try {
					throw new TransportException(new URL(objectsUrl, INFO_REFS)
							+ ": cannot read available refs", err);
				} catch (MalformedURLException mue) {
					throw new TransportException(objectsUrl + INFO_REFS
							+ ": cannot read available refs", err);
				}
			}
		}

		private Map<String, Ref> readAdvertisedImpl(final BufferedReader br)
