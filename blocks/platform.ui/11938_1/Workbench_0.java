		if (getWorkbenchConfigurer().getSaveAndRestore()) {
			SafeRunner.run(new SafeRunnable() {
				public void run() {
					persistWorkbenchState();
				}

				public void handleException(Throwable e) {
					String message;
					if (e.getMessage() == null) {
						message = WorkbenchMessages.ErrorClosingNoArg;
					} else {
						message = NLS.bind(WorkbenchMessages.ErrorClosingOneArg, e.getMessage());
					}

					if (!MessageDialog.openQuestion(null, WorkbenchMessages.Error, message)) {
						isClosing = false;
					}
				}
			});
		}

