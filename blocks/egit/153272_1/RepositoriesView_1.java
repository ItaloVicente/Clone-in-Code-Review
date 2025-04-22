
		@Override
		public void refresh() {
			super.refresh();
			afterRefresh(this);
		}

		@Override
		public void refresh(boolean updateLabels) {
			super.refresh(updateLabels);
			afterRefresh(this);
		}
