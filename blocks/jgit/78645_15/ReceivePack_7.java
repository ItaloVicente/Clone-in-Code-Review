		pckIn = new PacketLineIn(rawIn);
		pckOut = new PacketLineOut(rawOut);
		pckOut.setFlushOnEnd(false);

		enabledCapabilities = new HashSet<String>();
		commands = new ArrayList<ReceiveCommand>();
	}

	private Map<String
		if (refs == null)
			setAdvertisedRefs(null
		return refs;
	}
