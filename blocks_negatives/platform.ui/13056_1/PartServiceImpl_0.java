			MPart lastActivePart = activePart;
			activePart = p;

			if (constructed && !listeners.isEmpty()) {
				if (lastActivePart != null && lastActivePart != activePart) {
					firePartDeactivated(lastActivePart);
				}
			}
