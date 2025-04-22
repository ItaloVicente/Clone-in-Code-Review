		int previousAct;
		try {
			previousAct = settings.getInt(PREF_ACT);
		} catch (NumberFormatException e) {
			previousAct = ACTION_AUTO_SHARE;
		}
		switch (previousAct) {
		case ACTION_AUTO_SHARE:
			actionAutoShare.setSelection(true);
			break;
		case ACTION_NO_SHARE:
			actionNothing.setSelection(true);
			break;
		}

