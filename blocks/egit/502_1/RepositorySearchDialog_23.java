					try {
						prefs.put(PREF_PATH, file.getCanonicalPath());
						try {
							prefs.flush();
						} catch (BackingStoreException e1) {
						}
					} catch (IOException e2) {
					}

