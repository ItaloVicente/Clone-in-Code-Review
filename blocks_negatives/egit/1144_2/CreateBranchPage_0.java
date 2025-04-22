
			String fullName = getBranchName();
			try {
				if (myRepository.getRef(fullName) != null)
					setErrorMessage(NLS.bind(
							UIText.CreateBranchPage_BranchAlreadyExistsMessage,
							fullName));
				return;
			} catch (IOException e) {
			}
