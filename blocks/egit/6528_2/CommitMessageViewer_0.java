
		final MenuManager mgr = new MenuManager();
		Control c = getControl();
		c.setMenu(mgr.createContextMenu(c));

		IPersistentPreferenceStore pstore = (IPersistentPreferenceStore) store;

		Action showTagSequence = new BooleanPrefAction(pstore, UIPreferences.HISTORY_SHOW_TAG_SEQUENCE, UIText.ResourceHistory_ShowTagSequence) {
			@Override
			protected void apply(boolean value) {
			}
		};
		mgr.add(showTagSequence);

		Action wrapComments = new BooleanPrefAction(pstore, UIPreferences.RESOURCEHISTORY_SHOW_COMMENT_WRAP, UIText.ResourceHistory_toggleCommentWrap) {
			@Override
			protected void apply(boolean value) {
			}
		};
		mgr.add(wrapComments);

		Action fillParagraphs = new BooleanPrefAction(pstore, UIPreferences.RESOURCEHISTORY_SHOW_COMMENT_FILL, UIText.ResourceHistory_toggleCommentFill) {
			@Override
			protected void apply(boolean value) {
			}
		};
		mgr.add(fillParagraphs);

