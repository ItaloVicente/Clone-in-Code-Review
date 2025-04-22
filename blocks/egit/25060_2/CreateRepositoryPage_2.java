
			if (hasExistingFiles && !getBare()) {
				setMessage(NLS.bind(
						UIText.CreateRepositoryPage_NotEmptyMessage, dir),
						IMessageProvider.INFORMATION);
			} else
				setMessage(UIText.CreateRepositoryPage_PageMessage);
