
		@Override
		public void propertyChange(PropertyChangeEvent event) {
			if (!UIPreferences.REFESH_INDEX_INTERVAL
					.equals(event.getProperty())) {
				return;
			}
			updateRefreshInterval();
		}

		private void updateRefreshInterval() {
			interval = getRefreshIndexInterval();
			setReschedule(interval > 0);
			cancel();
			schedule(interval);
		}

		private static int getRefreshIndexInterval() {
			return 1000 * getDefault().getPreferenceStore()
					.getInt(UIPreferences.REFESH_INDEX_INTERVAL);
		}
