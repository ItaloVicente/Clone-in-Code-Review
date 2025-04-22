			} catch (PartInitException e) {
				String msg = NLS.bind(IDEWorkbenchMessages.OpenDelayedFileAction_message_errorOnOpen,
						details.fileStore.getName());
				CoreException eLog = new PartInitException(e.getMessage());
				IDEWorkbenchPlugin.log(msg, new Status(IStatus.ERROR, IDEApplication.PLUGIN_ID, msg, eLog));
				MessageDialog.open(MessageDialog.ERROR, window.getShell(),
						IDEWorkbenchMessages.OpenDelayedFileAction_title, msg, SWT.SHEET);
