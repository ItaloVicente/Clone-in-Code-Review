			Map<String
			try {
				localRefs = transport.local.getRefDatabase().getRefs(ALL);
			} catch (IOException e) {
				throw new TransportException(transport.uri
			}
			for (final Ref r : localRefs.values()) {
