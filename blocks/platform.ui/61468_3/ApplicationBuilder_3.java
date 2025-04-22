	private void importToolbarsLocation(MPerspective persp) {
		String trimsData = persp.getPersistedState().get("trims"); //$NON-NLS-1$
		if (trimsData == null || trimsData.trim().isEmpty()) {
			return;
		}
		persp.getPersistedState().remove("trims"); //$NON-NLS-1$
		Map<String, String> minMaxPersState = getMinMaxPersistedState();
		if (minMaxPersState == null) {
			return;
		}
		minMaxPersState.put(persp.getElementId(), trimsData);
	}

	private Map<String, String> getMinMaxPersistedState() {
		if (minMaxPersistedState != null) {
			return minMaxPersistedState;
		}
		for (MAddon addon : application.getAddons()) {
			if ("MinMax Addon".equals(addon.getElementId())) { //$NON-NLS-1$
				minMaxPersistedState = addon.getPersistedState();
				break;
			}
		}
		return minMaxPersistedState;
	}
