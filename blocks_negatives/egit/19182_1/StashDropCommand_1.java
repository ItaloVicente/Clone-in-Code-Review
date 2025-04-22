				try {
					op.execute(monitor);
				} catch (CoreException e) {
					Activator.logError(MessageFormat.format(
							UIText.StashDropCommand_dropFailed, commit.name()),
							e);
