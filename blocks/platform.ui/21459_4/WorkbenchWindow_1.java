			if (getModel().getPersistedState().containsKey(
					IPreferenceConstants.PERSPECTIVEBAR_VISIBLE)) {
				this.perspectiveBarVisible = Boolean.parseBoolean(getModel().getPersistedState()
						.get(IPreferenceConstants.PERSPECTIVEBAR_VISIBLE));
			} else {
				this.perspectiveBarVisible = PrefUtil.getInternalPreferenceStore().getBoolean(
						IPreferenceConstants.PERSPECTIVEBAR_VISIBLE);
				getModel().getPersistedState().put(IPreferenceConstants.PERSPECTIVEBAR_VISIBLE,
						Boolean.toString(this.perspectiveBarVisible));
