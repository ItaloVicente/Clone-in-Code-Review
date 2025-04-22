		if (part == null) {
			if (constructed && activePart != null) {
				firePartDeactivated(activePart);
			}
			activePart = part;
			return;
		}

