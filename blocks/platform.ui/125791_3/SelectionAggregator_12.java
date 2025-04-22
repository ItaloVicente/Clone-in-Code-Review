						runExternalCode(new Runnable() {
							@Override
							public void run() {
								notifyTargetedPostListeners(part, postSelection);
							}
						});
