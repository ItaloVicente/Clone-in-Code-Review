
				SubMonitor progress = SubMonitor.convert(monitor,
						events.size());
				try {
					for (WorkingTreeModifiedEvent event : events) {
						refreshRepository(event, progress.newChild(1));
					}
				} catch (OperationCanceledException oe) {
					return Status.CANCEL_STATUS;
				} catch (CoreException e) {
					handleError(UIText.Activator_refreshFailed, e, false);
					return new Status(IStatus.ERROR, getPluginId(),
							e.getMessage());
