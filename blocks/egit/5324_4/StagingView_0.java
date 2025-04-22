
		String chIdLine = "Change-Id: I" + ObjectId.zeroId().name(); //$NON-NLS-1$

		if (currentRepository.getConfig().getBoolean(
				ConfigConstants.CONFIG_GERRIT_SECTION,
				ConfigConstants.CONFIG_KEY_CREATECHANGEID, false)
				&& commitMessageComponent.getCreateChangeId()) {
			if (message.trim().equals(chIdLine))
				return false;

			message = message.replace(chIdLine, ""); //$NON-NLS-1$
		}

		if (org.eclipse.egit.ui.Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.COMMIT_DIALOG_SIGNED_OFF_BY)
				&& commitMessageComponent.isSignedOff()
				&& message.trim().equals(
						Constants.SIGNED_OFF_BY_TAG
								+ commitMessageComponent.getCommitter()))
			return false;

