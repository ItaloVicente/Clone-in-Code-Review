					@Override
					public void run() throws Exception {
						listener.propertyChange(event);
					}

					@Override
					public void handleException(Throwable exception) {
					}
				};
				SafeRunner.run(safetyWrapper);
