					return Status.OK_STATUS;
					IDEWorkbenchPlugin
							.log(
									IDEWorkbenchMessages.ResourceEncodingFieldEditor_ErrorStoringMessage,
									e.getStatus());
					return e.getStatus();
				} catch (BackingStoreException e) {
					IDEWorkbenchPlugin.log(IDEWorkbenchMessages.ResourceEncodingFieldEditor_ErrorStoringMessage, e);
					return new Status(IStatus.ERROR, IDEWorkbenchPlugin.IDE_WORKBENCH, e.getMessage(), e);
