	private class ColumnAction extends Action implements IUpdate {

		private final int columnIndex;

		public ColumnAction(String text, int idx) {
			super(text, IAction.AS_CHECK_BOX);
			columnIndex = idx;
			update();
		}

		@Override
		public void run() {
			graph.setVisible(columnIndex, isChecked());
		}

		@Override
		public void update() {
			setChecked(graph.getTableView().getTable().getColumn(columnIndex)
					.getWidth() > 0);
		}
	}

