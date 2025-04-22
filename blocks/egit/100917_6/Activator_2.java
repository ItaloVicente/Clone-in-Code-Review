
				SubMonitor progress = SubMonitor.convert(monitor,
						changes.size());
				try {
					for (WorkingTreeChanges change : changes) {
						refreshRepository(change, progress.newChild(1));
					}
				} catch (OperationCanceledException oe) {
					return Status.CANCEL_STATUS;
				} catch (CoreException e) {
					handleError(UIText.Activator_refreshFailed, e, false);
					return new Status(IStatus.ERROR, getPluginId(),
							e.getMessage());
