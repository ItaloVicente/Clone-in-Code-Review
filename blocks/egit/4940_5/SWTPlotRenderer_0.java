		int maxLength;
		if (tag)
			maxLength = Activator.getDefault().getPreferenceStore()
					.getInt(UIPreferences.HISTORY_MAX_TAG_LENGTH);
		else if (branch)
			maxLength = Activator.getDefault().getPreferenceStore()
					.getInt(UIPreferences.HISTORY_MAX_BRANCH_LENGTH);
		else
			maxLength = MAX_LABEL_LENGTH;
		if (txt.length() > maxLength)
			txt = txt.substring(0, maxLength) + "\u2026"; // ellipsis "..." (in UTF-8) //$NON-NLS-1$
