
		rebaseSkip = UIIcons.REBASE_SKIP.createImage();
		rebaseAbort = UIIcons.REBASE_ABORT.createImage();
		rebaseContinue = UIIcons.REBASE_CONTINUE.createImage();
	}

	public Menu getMenu(Control parent) {
		Menu menu = new Menu(parent);
		Repository repo = getRepository();

		boolean rebaseing = isInRebasingState(repo);
		boolean canContinue = rebaseing && canContinue(repo);

		addMenuItem(menu, UIText.RebasePulldownAction_Continue, rebaseContinue,
				new ContinueRebaseCommand(), canContinue);
		addMenuItem(menu, UIText.RebasePulldownAction_Skip, rebaseSkip,
				new SkipRebaseCommand(), rebaseing);
		addMenuItem(menu, UIText.RebasePulldownAction_Abort, rebaseAbort,
				new AbortRebaseCommand(), rebaseing);
		return menu;
	}

	@Override
	public void dispose() {
		rebaseSkip.dispose();
		rebaseAbort.dispose();
		rebaseContinue.dispose();
		super.dispose();
	}

	@Override
	protected boolean shouldRunAction() {
		Repository repo = getRepository();
		return !isInRebasingState(repo);
	}

	private void addMenuItem(Menu parent, String itemName, Image image,
			AbstractRebaseCommandHandler action, boolean isEnabled) {
		MenuItem item = new MenuItem(parent, SWT.PUSH);
		item.setImage(image);
		item.setText(itemName);
		item.setEnabled(isEnabled);
		ExecutionEvent event = createExecutionEvent();
		ItemSelectionListener selectionListener = new ItemSelectionListener(
				action, event);
		item.addSelectionListener(selectionListener);
	}

	private Repository getRepository() {
		ExecutionEvent event = createExecutionEvent();
		return AbstractSharedCommandHandler.getRepository(event);
	}

	private boolean isInRebasingState(Repository repo) {
		if (repo == null)
			return false;

		RepositoryState state = repo.getRepositoryState();
		return state == RepositoryState.REBASING
				|| state == RepositoryState.REBASING_INTERACTIVE
				|| state == RepositoryState.REBASING_MERGE
				|| state == RepositoryState.REBASING_REBASING;
	}

	private boolean canContinue(Repository repo) {
		IndexDiffCache diffCache = org.eclipse.egit.core.Activator.getDefault()
				.getIndexDiffCache();
		if (diffCache != null) {
			IndexDiffCacheEntry entry = diffCache.getIndexDiffCacheEntry(repo);
			return entry != null
					&& entry.getIndexDiff().getConflicting().isEmpty();
		}
		return false;
	}

	private static class ItemSelectionListener implements SelectionListener {

		private final ExecutionEvent event;

		private final AbstractRebaseCommandHandler action;

		private ItemSelectionListener(AbstractRebaseCommandHandler action,
				ExecutionEvent event) {
			this.event = event;
			this.action = action;
		}

		public void widgetSelected(SelectionEvent selectionEvent) {
			try {
				action.execute(event);
			} catch (ExecutionException e) {
				Activator.logError(e.getMessage(), e);
			}
		}

		public void widgetDefaultSelected(SelectionEvent e) {
		}

