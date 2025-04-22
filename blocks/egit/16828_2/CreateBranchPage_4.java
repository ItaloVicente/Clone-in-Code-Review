			boolean basedOnLocalBranch = source.startsWith(Constants.R_HEADS);
			if (basedOnLocalBranch && upstreamConfig != UpstreamConfig.NONE)
				setMessage(UIText.CreateBranchPage_LocalBranchWarningMessage,
						IMessageProvider.INFORMATION);
			else
				setMessage(null);
