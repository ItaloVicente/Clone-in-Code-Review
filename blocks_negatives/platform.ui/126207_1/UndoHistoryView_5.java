					display.syncExec(new Runnable() {
						@Override
						public void run() {
							if (!viewer.getTable().isDisposed()) {
								viewer.refresh(true);
							}
