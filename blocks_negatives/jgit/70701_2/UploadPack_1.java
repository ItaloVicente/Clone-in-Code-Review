		if (biDirectionalPipe)
			sendAdvertisedRefs(new PacketLineOutRefAdvertiser(pckOut));
		else if (requestValidator instanceof AnyRequestValidator)
			advertised = Collections.emptySet();
		else
			advertised = refIdSet(getAdvertisedOrDefaultRefs().values());

