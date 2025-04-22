	private DisposeListener itemDeletionListener = new DisposeListener() {

		@Override
		public void widgetDisposed(DisposeEvent e) {
			setFocusCell(null);
		}

	};
