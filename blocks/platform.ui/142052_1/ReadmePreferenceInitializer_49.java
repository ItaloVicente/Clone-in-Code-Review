		IPreferenceStore store = ReadmePlugin.getDefault().getPreferenceStore();
		store.setDefault(IReadmeConstants.PRE_CHECK1, true);
		store.setDefault(IReadmeConstants.PRE_CHECK2, true);
		store.setDefault(IReadmeConstants.PRE_CHECK3, false);
		store.setDefault(IReadmeConstants.PRE_RADIO_CHOICE, 2);
		store.setDefault(IReadmeConstants.PRE_TEXT, MessageUtil.getString("Default_text")); //$NON-NLS-1$
	}
