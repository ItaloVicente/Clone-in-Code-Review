			boolean emptyRefName = (createBranchSelected && branchText
					.getText().length() == 0)
					|| (createTagSelected && tagText.getText().length() == 0);
			if (emptyRefName) {
				setErrorMessage(UIText.FetchGerritChangePage_ProvideRefNameMessage);
				return;
			}

			boolean existingRefName = (createBranchSelected && repository
					.getRef(branchText.getText()) != null)
					|| (createTagSelected && repository.getRef(tagText
							.getText()) != null);
			if (existingRefName) {
				setErrorMessage(NLS.bind(
						UIText.FetchGerritChangePage_ExistingRefMessage,
						branchText.getText()));
				return;
			}
		} catch (IOException e1) {
