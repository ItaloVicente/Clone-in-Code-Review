
	private void createTag() {
		TagInputDialog inputDialog = new TagInputDialog(repo, getShell(),
				UIText.BranchSelectionDialog_QuestionNewTagMessage);
		if (inputDialog.open() == Window.OK) {
			String newRefName = Constants.R_TAGS + inputDialog.getValue();
			String tagMessage = inputDialog.getMessage();

			try {
				Tag tag = new Tag(repo);
				tag.setMessage(tagMessage);
				tag.setTagger(new PersonIdent(repo));
				tag.setTag(inputDialog.getValue());

				ObjectId startAt;
				if (refName == null)
					startAt = repo.resolve(Constants.HEAD);
				else
					startAt = repo.resolve(refName);
				tag.setObjId(startAt);
				tag.setType(Constants.typeString(repo.openObject(startAt)
						.getType()));

				ObjectWriter objWriter = new ObjectWriter(repo);
				tag.setTagId(objWriter.writeTag(tag));

				RefUpdate tagRef = repo.updateRef(newRefName);
				tagRef.setNewObjectId(tag.getTagId());
				tagRef.setRefLogMessage("tag: " + newRefName, false); //$NON-NLS-1$

				Result updateResult = tagRef.update();
				if (updateResult != Result.NEW)
					reportError(
							null,
							UIText.BranchSelectionDialog_BranchSelectionDialog_CreateTagFailedTitle,
							UIText.BranchSelectionDialog_ErrorCouldNotCreateNewRef,
							newRefName, tagMessage);
			} catch (Throwable e) {
				reportError(
						e,
						UIText.BranchSelectionDialog_BranchSelectionDialog_CreateTagFailedTitle,
						UIText.BranchSelectionDialog_ErrorCouldNotCreateNewRef,
						newRefName, tagMessage);
			}
			reloadTree(newRefName);
		}
	}

	private void reloadTree(String newRefName) {
		try {
			branchTree.removeAll();
			fillTreeWithBranches(newRefName);
		} catch (Throwable e1) {
			reportError(
					e1,
					UIText.BranchSelectionDialog_BranchSelectionDialog_CreateBranchFailedTitle,
					UIText.BranchSelectionDialog_ErrorCouldNotRefreshBranchList);
		}
	}

	private void renameRef() {
		String prompt;
		String refBase;
		String branchName;
		if (refName.startsWith(Constants.R_HEADS)) {
			refBase = Constants.R_HEADS;
			branchName = refName.substring(Constants.R_HEADS.length());
			prompt = UIText.BranchSelectionDialog_QuestionNewBranchNameMessage;
		} else if (refName.startsWith(Constants.R_TAGS)) {
			refBase = Constants.R_TAGS;
			branchName = refName.substring(Constants.R_TAGS.length());
			prompt = UIText.BranchSelectionDialog_QuestionNewTagNameMessage;
		} else {
			return;
		}
		InputDialog labelDialog = getRefNameInputDialog(NLS.bind(
				prompt,
				branchName));
		if (labelDialog.open() == Window.OK) {
			String newRefName = refBase + labelDialog.getValue();
			try {
				RefRename renameRef = repo.renameRef(refName, newRefName);
				if (renameRef.rename() != Result.RENAMED) {
					reportError(
							null,
							UIText.BranchSelectionDialog_BranchSelectionDialog_RenamedFailedTitle,
							UIText.BranchSelectionDialog_ErrorCouldNotRenameRef,
							refName, newRefName, renameRef.getResult());
				}
			} catch (Throwable e1) {
				reportError(
						e1,
						UIText.BranchSelectionDialog_BranchSelectionDialog_RenamedFailedTitle,
						UIText.BranchSelectionDialog_ErrorCouldNotRenameRef,
						refName, newRefName, e1.getMessage());
			}
			reloadTree(newRefName);
		}
	}
