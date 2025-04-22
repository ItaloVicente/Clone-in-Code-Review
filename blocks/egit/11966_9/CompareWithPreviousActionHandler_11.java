
	private PreviousCommit getPreviousRevision(final ExecutionEvent event,
			final IResource resource) {
		final List<PreviousCommit> previousList;
		try {
			previousList = findPreviousCommits();
		} catch (IOException e) {
			Activator.handleError(e.getMessage(), e, true);
			return null;
		}

		final AtomicReference<PreviousCommit> previous = new AtomicReference<PreviousCommit>();
		if (previousList.size() == 0)
			showNotFoundDialog(event, resource);
		else if (previousList.size() == 1)
			previous.set(previousList.get(0));
		else {
			final List<RevCommit> commits = new ArrayList<RevCommit>();
			for (PreviousCommit pc : previousList)
				commits.add(pc.commit);
			HandlerUtil.getActiveShell(event).getDisplay()
					.syncExec(new Runnable() {
						public void run() {
							CommitSelectDialog dlg = new CommitSelectDialog(
									HandlerUtil.getActiveShell(event), commits);
							if (dlg.open() == Window.OK)
								for (PreviousCommit pc : previousList)
									if (pc.commit.equals(dlg
											.getSelectedCommit())) {
										previous.set(pc);
										break;
									}
						}
					});
		}

		return previous.get();
	}

	private void showNotFoundDialog(ExecutionEvent event, IResource resource) {
		final Shell shell = HandlerUtil.getActiveShell(event);
		final String message = MessageFormat
				.format(UIText.CompareWithPreviousActionHandler_MessageRevisionNotFound,
						resource.getName());
		shell.getDisplay().asyncExec(new Runnable() {

			public void run() {
				MessageDialog
						.openWarning(
								shell,
								UIText.CompareWithPreviousActionHandler_TitleRevisionNotFound,
								message);
			}
		});
	}
