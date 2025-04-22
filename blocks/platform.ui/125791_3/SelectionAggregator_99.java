						runExternalCode(new Runnable() {
							@Override
							public void run() {
								notifyTargetedListeners(part, selection);
							}
						});
