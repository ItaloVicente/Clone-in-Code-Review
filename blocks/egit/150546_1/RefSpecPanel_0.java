		private final Map<TableItem, Button> buttons = new HashMap<>();

		public NativeCheckboxLabelProvider(int index) {
			this.index = index;
		}

		@Override
		public void dispose() {
			buttons.clear();
			super.dispose();
		}

		@Override
		public String getText(Object element) {
			return null;
		}

		@Override
		public String getToolTipText(Object element) {
			if (element instanceof RefSpec) {
				RefSpec refSpec = (RefSpec) element;
				if (isDeleteRefSpec(refSpec)) {
