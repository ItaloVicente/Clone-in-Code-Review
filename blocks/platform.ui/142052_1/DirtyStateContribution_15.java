		boolean saveNeeded = false;
		if (activeEditor != null)
			saveNeeded = activeEditor.isDirty();
		if (saveNeeded)
			label.setText(MessageUtil.getString("Save_Needed")); //$NON-NLS-1$
		else
			label.setText(MessageUtil.getString("Clean")); //$NON-NLS-1$
	}
