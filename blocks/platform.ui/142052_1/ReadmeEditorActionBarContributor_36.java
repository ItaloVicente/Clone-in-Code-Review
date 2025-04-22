		super.contributeToStatusLine(statusLineManager);
		statusLineManager.setMessage(MessageUtil.getString("Editor_is_active")); //$NON-NLS-1$
		statusLineManager.add(dirtyStateContribution);
	}

	@Override
