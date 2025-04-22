				StagingEntry stagingEntry = (StagingEntry) selection.getFirstElement();
				switch (stagingEntry.getState()) {
				case ADDED:
					addUnstage = true;
					break;
				case CHANGED:
					addReplaceWithHeadRevision = true;
					addUnstage = true;
					break;
				case REMOVED:
					addReplaceWithHeadRevision = true;
					addUnstage = true;
					break;
				case CONFLICTING:
					addReplaceWithFileInGitIndex = true;
					addReplaceWithHeadRevision = true;
					addStage = true;
					addLaunchMergeTool = true;
					break;
				case MISSING:
				case MODIFIED:
				case PARTIALLY_MODIFIED:
				case UNTRACKED:
					addReplaceWithFileInGitIndex = true;
					addReplaceWithHeadRevision = true;
					addStage = true;
					break;
				}
