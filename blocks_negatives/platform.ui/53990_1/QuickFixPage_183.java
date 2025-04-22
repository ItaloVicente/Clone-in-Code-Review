				getWizard().getContainer().run(false, true, new IRunnableWithProgress() {

					@Override
					public void run(IProgressMonitor monitor1) {
						monitor1.beginTask(MarkerMessages.MarkerResolutionDialog_Fixing, checked.length);
						for (int i = 0; i < checked.length; i++) {
							getShell().getDisplay().readAndDispatch();
							if (monitor1.isCanceled()) {
								return;
							}
							IMarker marker = (IMarker) checked[i];
							monitor1.subTask(Util.getProperty(IMarker.MESSAGE, marker));
							resolution.run(marker);
							monitor1.worked(1);
