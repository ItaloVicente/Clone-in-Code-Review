				RefUpdate refUpdate = repo.updateRef(Constants.HEAD, false);
				refUpdate.setRefLogMessage("rebase: aborting", false); //$NON-NLS-1$
				Result res = refUpdate.link(headName);
				switch (res) {
				case FAST_FORWARD:
				case FORCED:
				case NO_CHANGE:
					break;
				default:
					throw new JGitInternalException(
							JGitText.get().abortingRebaseFailed);
				}
