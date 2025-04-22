		try {
			final IEclipseContext windowContext = model.getContext();
			HandlerServiceImpl.push(windowContext.getParent(), null);

			if (getModel().getPersistedState().containsKey(IPreferenceConstants.COOLBAR_VISIBLE)) {
				this.coolBarVisible = Boolean.parseBoolean(getModel().getPersistedState().get(
						IPreferenceConstants.COOLBAR_VISIBLE));
			} else {
				this.coolBarVisible = PrefUtil.getInternalPreferenceStore().getBoolean(
						IPreferenceConstants.COOLBAR_VISIBLE);
				getModel().getPersistedState().put(IPreferenceConstants.COOLBAR_VISIBLE,
						Boolean.toString(this.coolBarVisible));
