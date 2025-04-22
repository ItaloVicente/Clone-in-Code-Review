	private IAction createFindToolbarAction() {
		final IAction r = new Action(UIText.GitHistoryPage_FindMenuLabel,
				UIIcons.ELCL16_FIND) {
			public void run() {
				store.setValue(UIPreferences.RESOURCEHISTORY_SHOW_FINDTOOLBAR,
						isChecked());
				if (store.needsSaving()) {
					try {
						store.save();
					} catch (IOException e) {
						Activator.handleError(e.getMessage(), e, false);
					}
				}
				layout();
			}
		};
		r.setChecked(store
				.getBoolean(UIPreferences.RESOURCEHISTORY_SHOW_FINDTOOLBAR));
		r.setToolTipText(UIText.GitHistoryPage_FindTooltip);
		return r;
	}

	private IAction createCommentWrap() {
		final BooleanPrefAction a = new BooleanPrefAction(
				UIPreferences.RESOURCEHISTORY_SHOW_COMMENT_WRAP,
				UIText.ResourceHistory_toggleCommentWrap) {
			void apply(boolean wrap) {
			}
		};
		a.apply(a.isChecked());
		actionsToDispose.add(a);
		return a;
	}

	private IAction createCommentFill() {
		final BooleanPrefAction a = new BooleanPrefAction(
				UIPreferences.RESOURCEHISTORY_SHOW_COMMENT_FILL,
				UIText.ResourceHistory_toggleCommentFill) {
			void apply(boolean fill) {
			}
		};
		a.apply(a.isChecked());
		actionsToDispose.add(a);
		return a;
	}

	private IAction createShowComment() {
		BooleanPrefAction a = new BooleanPrefAction(
				UIPreferences.RESOURCEHISTORY_SHOW_REV_COMMENT,
				UIText.ResourceHistory_toggleRevComment) {
			void apply(final boolean value) {
				layout();
				wrapCommentAction.setEnabled(isChecked());
				fillCommentAction.setEnabled(isChecked());
			}
		};
		actionsToDispose.add(a);
		return a;
	}

	private IAction createShowFiles() {
		BooleanPrefAction a = new BooleanPrefAction(
				UIPreferences.RESOURCEHISTORY_SHOW_REV_DETAIL,
				UIText.ResourceHistory_toggleRevDetail) {
			void apply(final boolean value) {
				layout();
			}
		};
		actionsToDispose.add(a);
		return a;
	}

