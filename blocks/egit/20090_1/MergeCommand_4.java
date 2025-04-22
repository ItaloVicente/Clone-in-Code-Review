
			refName = mergeTargetSelectionDialog.getRefName();
			op = new MergeOperation(repository, refName);
			op.setSquash(mergeTargetSelectionDialog.isMergeSquash());
			op.setFastForwardMode(mergeTargetSelectionDialog
					.getFastForwardMode());
			op.setCommit(mergeTargetSelectionDialog.isCommit());
