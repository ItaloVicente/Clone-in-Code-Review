					if (currentIndex >= listeners.length) {
						resetState();
						if (!hasPendingUpdates()) {
							decorated();
						}
						labelProviderChangedEvent = null;
						listeners = EMPTY_LISTENER_LIST;
					} else {
						schedule(UPDATE_DELAY);// Reschedule if we are not done
