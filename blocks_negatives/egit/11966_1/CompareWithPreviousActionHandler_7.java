	private class CompareWithPreviousOperation implements IEGitOperation {

		private ExecutionEvent event;

		private Repository repository;

		private IResource resource;

		private CompareWithPreviousOperation(ExecutionEvent event,
				Repository repository, IResource resource) {
			this.event = event;
			this.repository = repository;
			this.resource = resource;
		}

		public void execute(IProgressMonitor monitor) throws CoreException {
			final List<PreviousCommit> previousList;
			try {
				previousList = findPreviousCommits();
			} catch (IOException e) {
				Activator.handleError(e.getMessage(), e, true);
				return;
			}
			final AtomicReference<PreviousCommit> previous = new AtomicReference<PreviousCommit>();
			if (previousList.size() == 0) {
				showNotFoundDialog();
				return;
			} else if (previousList.size() > 1){
				final List<RevCommit> commits = new ArrayList<RevCommit>();
				for (PreviousCommit pc: previousList)
					commits.add(pc.commit);
				HandlerUtil.getActiveShell(event).getDisplay()
						.syncExec(new Runnable() {
							public void run() {
								CommitSelectDialog dlg = new CommitSelectDialog(
										HandlerUtil.getActiveShell(event),
										commits);
								if (dlg.open() == Window.OK)
									for (PreviousCommit pc: previousList)
										if (pc.commit.equals(dlg.getSelectedCommit())){
											   previous.set(pc);
											   break;
										   }
							}
						});
			}
			else
				previous.set(previousList.get(0));

			if (previous.get() == null)
				return;
			if (resource instanceof IFile) {
				final ITypedElement base = SaveableCompareEditorInput
						.createFileElement((IFile) resource);
				PreviousCommit pc = previous.get();
				ITypedElement next = CompareUtils.getFileRevisionTypedElement(
						pc.path, pc.commit, repository);
				CompareEditorInput input = new GitCompareFileRevisionEditorInput(
						base, next, null);
				CompareUI.openCompareEditor(input);
			} else
				openCompareTreeView(previous.get().commit);
		}

		private void openCompareTreeView(final RevCommit previous) {
			final Shell shell = HandlerUtil.getActiveShell(event);
			shell.getDisplay().asyncExec(new Runnable() {

				public void run() {
					try {
						CompareTreeView view = (CompareTreeView) PlatformUI
								.getWorkbench().getActiveWorkbenchWindow()
								.getActivePage().showView(CompareTreeView.ID);
						view.setInput(new IResource[] { resource },
								previous.name());
					} catch (PartInitException e) {
						Activator.handleError(e.getMessage(), e, true);
					}
				}
			});
		}

		private void showNotFoundDialog() {
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

		public ISchedulingRule getSchedulingRule() {
			return resource;
		}
	}

