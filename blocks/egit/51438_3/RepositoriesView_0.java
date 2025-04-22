				final CommonViewer tv = getCommonViewer();
				if (!UIUtils.isUsable(tv)) {
					return Status.CANCEL_STATUS;
				}
				final boolean trace = GitTraceLocation.REPOSITORIESVIEW
						.isActive();
				final boolean needsNewInput = lastInputChange > lastInputUpdate;
				if (trace) {
					trace("Running the update, new input required: " //$NON-NLS-1$
									+ (lastInputChange > lastInputUpdate));
				}
