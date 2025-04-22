			this.table = table;
		}

		@Override
		public void layout() {
			if (Util.isGtk() && SWT.getVersion() <= 4924) {
				TableItem item = getItem();
				if (item != null) {
					Rectangle rect = item.getBounds();
					if (table.getHeaderVisible()) {
						Control editor = getEditor();
						if (editor != null && !editor.isDisposed()) {
							editor.setVisible(
									rect.y >= table.getHeaderHeight());
						}
					}
				}
			}
			super.layout();
