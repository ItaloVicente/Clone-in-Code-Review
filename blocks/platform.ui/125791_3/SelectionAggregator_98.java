						runExternalCode(new Runnable() {
							@Override
							public void run() {
								notifyListeners(part, selection);
							}
						});
