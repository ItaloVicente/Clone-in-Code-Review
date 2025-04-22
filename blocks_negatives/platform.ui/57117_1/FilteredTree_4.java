				getDisplay().asyncExec(new Runnable() {
					@Override
					public void run() {
						if (!filterText.isDisposed()
								&& filterText.isFocusControl()) {
							setFilterText(initialText);
							textChanged();
						}
