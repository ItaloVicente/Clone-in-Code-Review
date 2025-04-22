				refschangedRunnable = () -> {
					if (!getControl().isDisposed()) {
						if (GitTraceLocation.HISTORYVIEW.isActive())
							GitTraceLocation
							.getTrace()
							.trace(
									GitTraceLocation.HISTORYVIEW
									.getLocation(),
									"Executing async repository changed event"); //$NON-NLS-1$
						refschangedRunnable = null;
						initAndStartRevWalk(
								!(e instanceof FetchHeadChangedEvent));
