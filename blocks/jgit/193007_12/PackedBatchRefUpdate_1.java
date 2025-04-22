	@Override
	protected boolean isRefLogDisabled(ReceiveCommand cmd) {
		return cmd.getRefName().startsWith(Constants.R_REMOTES)
				|| super.isRefLogDisabled(cmd);
	}

