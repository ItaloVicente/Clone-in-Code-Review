
		@Override
		public void propertyChange(PropertyChangeEvent event) {
			if (!UIPreferences.REFESH_INDEX_INTERVAL
					.equals(event.getProperty())) {
				return;
			}
			updateRefreshInterval();
		}

		void updateRefreshInterval() {
			interval = getRefreshIndexInterval();
			if (interval > 0) {
				setReschedule(true);
				schedule(interval);
			} else {
				setReschedule(false);
				cancel();
			}
		}

		static int getRefreshIndexInterval() {
			return 1000 * getDefault().getPreferenceStore()
					.getInt(UIPreferences.REFESH_INDEX_INTERVAL);
		}
