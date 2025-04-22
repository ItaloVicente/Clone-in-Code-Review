				if (data.throwExceptionInBackground) {
					return new Status(IStatus.ERROR, "org.eclipse.ui.tests", "Saving in the background failed");
				}
				data.setOutput(data.getInput());
				setDirty(false);
				monitor1.done();
				return Status.OK_STATUS;
