			InputDialog dlg = new InputDialog(HandlerUtil
					.getActiveShellChecked(event),
					UIText.BranchSelectionDialog_QuestionNewBranchTitle,
					prompt, "", ValidationUtils //$NON-NLS-1$
							.getRefNameInputValidator(repo, Constants.R_HEADS));
			if (dlg.open() != Window.OK)
				return null;
			RefUpdate updateRef = repo.updateRef(Constants.R_HEADS
					+ dlg.getValue());
			updateRef.setNewObjectId(startAt);
			updateRef.setRefLogMessage(
					"branch: Created from " + startAt.name(), false); //$NON-NLS-1$
			updateRef.update();
		} catch (IOException e) {
			throw new ExecutionException(e.getMessage(), e);
		}
