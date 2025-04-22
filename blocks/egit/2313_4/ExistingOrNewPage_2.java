		private final Row[] subrows;

		public Row(boolean selected, String project, String path, String repository) {
			this(selected, project, path, repository, null);
		}

		public Row(boolean selected, String project, String path, String repository, Row[] subrows) {
			this.selected = selected;
