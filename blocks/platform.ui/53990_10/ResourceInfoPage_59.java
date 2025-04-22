			} catch (CoreException e1) {
				IDEWorkbenchPlugin
						.log(IDEWorkbenchMessages.ResourceInfo_recursiveChangesError,
								e1.getStatus());
				return e1.getStatus();
			} catch (OperationCanceledException e2) {
				return Status.CANCEL_STATUS;
			} finally {
				monitor.done();
