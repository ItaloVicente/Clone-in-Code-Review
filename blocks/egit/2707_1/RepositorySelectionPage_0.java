	private boolean canHandleProtocol(URIish u) {
		for (TransportProtocol proto : Transport.getTransportProtocols()) {
			if (proto.canHandle(u))
				return true;
		}

		return false;
	}

