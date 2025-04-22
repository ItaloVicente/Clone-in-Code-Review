					if (!resized) {
						resized = true;
						e.display.timerExec(100, new Runnable() {
							@Override
							public void run() {
								if (table != null && !table.isDisposed() && filterText !=null && !filterText.isDisposed()) {
									refresh(filterText.getText().toLowerCase());
								}
								resized = false;
							}
						});
