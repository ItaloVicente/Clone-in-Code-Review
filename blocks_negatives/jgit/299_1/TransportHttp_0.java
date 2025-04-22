			try {
				readAdvertisedRefs();
			} catch (IOException err) {
				close();
				throw new TransportException(uri, "remote hung up", err);
			}
