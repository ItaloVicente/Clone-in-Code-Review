		public synchronized ITheme registerTheme(String id, String label,
				String basestylesheetURI, String osVersion) throws IllegalArgumentException {
			for (Theme t : themes) {
				if (t.getId().equals(id)) {
					throw new IllegalArgumentException("A theme with the id '" + id
							+ "' is already registered");
				}
