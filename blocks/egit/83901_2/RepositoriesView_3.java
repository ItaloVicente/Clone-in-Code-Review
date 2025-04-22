						if (monitor1.isCanceled()) {
							return Status.CANCEL_STATUS;
						}
						if (uiTask != null) {
							uiTask.run();
						}
						return Status.OK_STATUS;
