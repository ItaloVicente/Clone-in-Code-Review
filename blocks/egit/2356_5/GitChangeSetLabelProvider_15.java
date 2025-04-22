	private String createChangeSetLabel(GitModelCommit commit) {
		String format = store.getString(UIPreferences.SYNC_VIEW_CHANGESET_LABEL_FORMAT);

		RevCommit baseCommit = commit.getBaseCommit();
		Map<String, String> bindings = new HashMap<String, String>();
		bindings.put(BINDING_CHANGESET_DATE, DATE_FORMAT.format(baseCommit.getAuthorIdent().getWhen()));
		bindings.put(BINDING_CHANGESET_AUTHOR, baseCommit.getAuthorIdent().getName());
		bindings.put(BINDING_CHANGESET_COMMITTER, baseCommit.getCommitterIdent().getName());
		bindings.put(BINDING_CHANGESET_SHORT_MESSAGE, baseCommit.getShortMessage());

		return formatName(format, bindings);
	}

	public static String formatName(final String format, Map<String, String> bindings) {
		String result = format;
		for (Entry<String, String> e : bindings.entrySet()) {
			result = result.replace(e.getKey(), e.getValue());
		}
		return result;
	}

