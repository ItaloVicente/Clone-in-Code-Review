		this.remoteMode = remote;
		if (this.remoteMode)
			if (baseBranch != null)
				setTitle(NLS.bind(
						UIText.CreateBranchPage_CreateRemoteBaseOnTitle,
						myBaseBranch.getName()));
			else
				setTitle(UIText.CreateBranchPage_CreateRemoteTitle);
		else if (baseBranch != null)
			setTitle(NLS.bind(UIText.CreateBranchPage_CreateLocalBasedTitle,
					myBaseBranch.getName()));
		else
			setTitle(UIText.CreateBranchPage_CreateLocalTitle);
