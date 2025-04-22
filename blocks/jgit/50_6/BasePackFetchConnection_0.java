
		if (statelessRPC && multiAck != MultiAck.DETAILED) {
			throw new PackProtocolException(uri
					+ OPTION_MULTI_ACK_DETAILED + " to be enabled");
		}

