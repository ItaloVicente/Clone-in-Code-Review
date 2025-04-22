		String confirmQuestion;
		if (nodes.size() == 1) {
			confirmQuestion = NLS.bind(
					UIText.RepositoriesView_ConfirmBranchDeletionMessage, nodes
							.get(0).getObject().getName());

		} else {
			confirmQuestion = NLS.bind(
					UIText.DeleteBranchCommand_DeleteMultiBranchQuestion,
					Integer.valueOf(nodes.size()));
		}

