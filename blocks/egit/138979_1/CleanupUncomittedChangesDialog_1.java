			if (needResult) {
				CommitUI commitUI = new CommitUI(getShell(), repository,
						new IResource[0], true);
				shouldContinue = commitUI.commit();
			} else {
				CommonUtils.runCommand(ActionCommands.COMMIT_ACTION,
						new StructuredSelection(repository));
			}
