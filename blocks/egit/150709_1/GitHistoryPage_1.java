		@Override
		public void dispose() {
			if (toolbar != null && !toolbar.isDisposed()) {
				toolbar.dispose();
			}
			super.dispose();
		}

