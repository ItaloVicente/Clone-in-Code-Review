				getWizard().getContainer().run(false, true, monitor1 -> {
					monitor1.beginTask(MarkerMessages.MarkerResolutionDialog_Fixing, checked.length);
					for (int i = 0; i < checked.length; i++) {
						getShell().getDisplay().readAndDispatch();
						if (monitor1.isCanceled()) {
							return;
