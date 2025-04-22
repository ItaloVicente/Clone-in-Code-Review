					amendingCommitInRemoteBranch = isContainedInAnyRemoteBranch(previousCommit);
					previousCommitMessage = headCommitInfo.getCommitMessage();
					previousAuthor = headCommitInfo.getAuthor();
				}
			}, true, new NullProgressMonitor(), Display.getDefault());
		} catch (InvocationTargetException e) {
			org.eclipse.egit.ui.Activator.error(e.getMessage(), e);
		} catch (InterruptedException e) {
			org.eclipse.egit.ui.Activator.error(e.getMessage(), e);
		}
