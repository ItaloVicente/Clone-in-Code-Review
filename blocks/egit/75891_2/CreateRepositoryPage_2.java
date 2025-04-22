			if (checker.hasContent()) {
				if (getBare()) {
					setErrorMessage(NLS.bind(
							UIText.CreateRepositoryPage_NotEmptyMessage, dir));
					return;
				} else {
					setMessage(NLS.bind(
							UIText.CreateRepositoryPage_NotEmptyMessage, dir),
							IMessageProvider.INFORMATION);
				}
			} else {
