
		@Override
		public boolean belongsTo(Object family) {
			return family == JobFamilies.HISTORY_DIFF
					|| super.belongsTo(family);
		}
