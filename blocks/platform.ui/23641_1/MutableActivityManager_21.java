			}
		}

		return identifierEventsByIdentifierId;
	}

	public void unhookRegistryListeners() {
		activityRegistry.removeActivityRegistryListener(activityRegistryListener);
	}

