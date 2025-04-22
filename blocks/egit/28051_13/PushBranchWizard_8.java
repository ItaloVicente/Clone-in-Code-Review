		if (ref != null) {
			return MessageFormat.format(UIText.PushBranchWizard_WindowTitle,
					Repository.shortenRefName(this.ref.getName()));
		} else {
			return UIText.PushCommitHandler_pushCommitTitle;
		}
