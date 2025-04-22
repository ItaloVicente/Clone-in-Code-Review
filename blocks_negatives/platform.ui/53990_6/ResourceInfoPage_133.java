				} catch (CoreException e) {
					IDEWorkbenchPlugin
							.log(IDEWorkbenchMessages.ResourceInfo_recursiveChangesError,
									e.getStatus());
					return e.getStatus();
				} catch (OperationCanceledException e) {
					return Status.CANCEL_STATUS;
				} finally {
					monitor.done();
