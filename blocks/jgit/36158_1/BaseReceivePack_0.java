
				boolean clientBehavior = advertisedCapabilites.containsAll(enabledCapabilities);
				if (!clientBehavior) {

					enabledCapabilities.retainAll(advertisedCapabilites);
				}
