		if (useProtocolV2) {
			for (String s : v2CapabilityAdvertisement) {
				adv.writeOne(s);
			}
			adv.end();
			return;
		}

