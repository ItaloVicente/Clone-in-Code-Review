		return new Listener() {

			@Override
			public void handleEvent(Event event) {
				switch (event.type) {
				case SWT.Paint:
					paint(event);
					break;

				case SWT.KeyDown:
					getParent().notifyListeners(SWT.KeyDown, event);
					ArrayList<Object> list = new ArrayList<>();
					for (ViewerCell cell : cells) {
						list.add(cell.getElement());
					}
					AbstractCellCursor.this.viewer
							.setSelection(new StructuredSelection(list));
					break;

				case SWT.MouseDown:
					if (event.time < activationTime) {
						Event cEvent = copyEvent(event);
						cEvent.type = SWT.MouseDoubleClick;
						getParent().notifyListeners(SWT.MouseDoubleClick,
								cEvent);
					} else {
						getParent().notifyListeners(SWT.MouseDown,
								copyEvent(event));
					}
					break;
