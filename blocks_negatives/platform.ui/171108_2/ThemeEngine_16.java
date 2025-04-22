		/**
		 * Broadcast theme-change event using OSGi Event Admin.
		 */
		private void sendThemeChangeEvent(boolean restore) {
			EventAdmin eventAdmin = getEventAdmin();
			if (eventAdmin == null) {
				return;
