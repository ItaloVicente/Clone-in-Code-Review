			if (cherryPickResult.getStatus() == CherryPickStatus.CONFLICTING)
				MessageDialog.openWarning(parent,
						UIText.CherryPickHandler_CherryPickConflictsTitle,
						UIText.CherryPickHandler_CherryPickConflictsMessage);
			else
				throw new ExecutionException(UIText.CherryPickOperation_Failed);

