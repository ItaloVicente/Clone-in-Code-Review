			Repository db = input.getRepository();
			if (resolveHead(db, true) == null) {
				this.name = ""; //$NON-NLS-1$
				setErrorMessage(UIText.GitHistoryPage_NoInputMessage);
				return false;
			}
