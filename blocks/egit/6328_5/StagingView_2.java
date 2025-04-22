				try {
					RevCommit headRev = null;
					try {
						final Ref head = currentRepository.getRef(Constants.HEAD);
						if (head.getObjectId() != null)
							headRev = new RevWalk(currentRepository).parseCommit(head
									.getObjectId());
					} catch (IOException e1) {
						syncOpenError(getSite().getShell(),
								UIText.CommitAction_MergeHeadErrorTitle,
								UIText.CommitAction_ErrorReadingMergeMsg);
						return new Status(IStatus.ERROR,
								Activator.getPluginId(),
								UIText.CommitAction_ErrorReadingMergeMsg);
					}
