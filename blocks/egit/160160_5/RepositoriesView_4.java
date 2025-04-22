
	private static class TreeFilterEditor extends ControlEditor {

		private Composite parent;

		private TreeItem item;

		private int columnIndex;

		private Point editorSize;

		public TreeFilterEditor(Composite parent, TreeItem item,
				int columnIndex, Control editor) {
			super(parent);
			this.parent = parent;
			this.item = item;
			this.columnIndex = columnIndex;
			editorSize = editor.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			setEditor(editor);
		}

		@Override
		public void layout() {
			Point textSize = null;
			GC gc = null;
			try {
				gc = new GC(parent.getDisplay());
				gc.setFont(item.getFont(columnIndex));
				textSize = gc.textExtent(item.getText(columnIndex));
			} finally {
				if (gc != null) {
					gc.dispose();
				}
			}
			Rectangle cell = item.getBounds(columnIndex);
			Rectangle text = item.getTextBounds(columnIndex);
			Rectangle area = parent.getClientArea();
			area.y = cell.y;
			area.x = Math.max(area.x, Math.min(text.x + textSize.x + 5,
					area.x + area.width - editorSize.x));
			area.width = editorSize.x;
			area.height = editorSize.y;
			getEditor().setBounds(area);
		}

		@Override
		public void dispose() {
			super.dispose();
			parent = null;
			item = null;
			editorSize = null;
		}
	}
