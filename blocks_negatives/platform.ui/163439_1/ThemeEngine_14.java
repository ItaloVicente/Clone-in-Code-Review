				}
			}

			if (restore) {
				IEclipsePreferences pref = getPreferences();
				EclipsePreferencesHelper.setPreviousThemeId(pref.get(THEMEID_KEY, null));
				EclipsePreferencesHelper.setCurrentThemeId(theme.getId());

				pref.put(THEMEID_KEY, theme.getId());
				try {
					pref.flush();
				} catch (BackingStoreException e) {
