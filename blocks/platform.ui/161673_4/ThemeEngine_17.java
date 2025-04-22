				pref.put(THEMEID_KEY, theme.getId());
				try {
					pref.flush();
				} catch (BackingStoreException e) {
					ThemeEngineManager.logError(e.getMessage(), e);
				}
