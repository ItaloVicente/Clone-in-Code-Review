			PlatformUI.getWorkbench().getProgressService().run(true, true,
					new IRunnableWithProgress() {
						/*
						 * (non-Javadoc)
						 *
						 * @see org.eclipse.jface.operation.IRunnableWithProgress#run(org.eclipse.core.runtime.IProgressMonitor)
						 */
						@Override
						public void run(IProgressMonitor monitor) {
							monitor.beginTask(
									MarkerMessages.markCompletedHandler_task,
									100);
							IMarker[] markers = getSelectedMarkers(finalEvent);
							if (markers.length == 0)
								return;
