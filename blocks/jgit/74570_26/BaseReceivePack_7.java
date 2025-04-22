		if (allowPushOptions) {
			adv.advertiseCapability(CAPABILITY_PUSH_OPTIONS);

			if (pushOptions == null) {
				pushOptions = new ArrayList<>();
			}
		}
