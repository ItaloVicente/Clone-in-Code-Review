						runExternalCode(new Runnable() {
							@Override
							public void run() {
								notifyPostListeners(part, postSelection);
							}
						});
