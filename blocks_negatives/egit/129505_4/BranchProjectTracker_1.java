		String branch = memento.getString(KEY_BRANCH);
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		String pref = getPreference(branch);
		StringWriter writer = new StringWriter();
		try {
			((XMLMemento) memento).save(writer);
			store.setValue(pref, writer.toString());
		} catch (IOException e) {
			Activator.logError("Error writing branch-project associations", e); //$NON-NLS-1$
		}
