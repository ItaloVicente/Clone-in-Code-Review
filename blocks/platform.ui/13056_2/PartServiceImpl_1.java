
		MPart lastActivePart = activePart;
		activePart = part;

		if (constructed && lastActivePart != null && lastActivePart != activePart) {
			firePartDeactivated(lastActivePart);
		}

