				DisposeListener listener = new DisposeListener() {

					@Override
					public void widgetDisposed(DisposeEvent e) {
						if (item.getData("EDITOR") != null) {
							TableEditor editor = (TableEditor) item
									.getData("EDITOR");
							editor.getEditor().dispose();
							editor.dispose();
						}
