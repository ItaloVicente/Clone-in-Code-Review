				final boolean trace = GitTraceLocation.REPOSITORIESVIEW
						.isActive();
				final boolean needsNewInput = lastInputChange > lastInputUpdate;
				if (trace) {
					trace("Running the update, new input required: " //$NON-NLS-1$
									+ (lastInputChange > lastInputUpdate));
				}
