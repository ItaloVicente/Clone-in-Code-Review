public class CompareWithPreviousActionHandler extends RepositoryActionHandler {

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

		private String getRepositoryPath() {
			return RepositoryMapping.getMapping(resource.getProject())
					.getRepoRelativePath(resource);
		}

		public void execute(IProgressMonitor monitor) throws CoreException {
			RevCommit previous = findPreviousCommit();
			if (previous != null)
				if (resource instanceof IFile) {
					final ITypedElement base = SaveableCompareEditorInput
							.createFileElement((IFile) resource);
					ITypedElement next = CompareUtils
							.getFileRevisionTypedElement(getRepositoryPath(),
									previous, repository);
					CompareEditorInput input = new GitCompareFileRevisionEditorInput(
							base, next, null);
					CompareUI.openCompareEditor(input);
				} else
					openCompareTreeView(previous);
			else
				showNotFoundDialog();
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

		private RevCommit findPreviousCommit() {
			RevWalk rw = new RevWalk(repository);
			try {
				String path = getRepositoryPath();
				if (path.length() > 0)
					rw.setTreeFilter(FollowFilter.create(path));
				RevCommit headCommit = rw.parseCommit(repository.getRef(
						Constants.HEAD).getObjectId());
				rw.markStart(headCommit);
				headCommit = rw.next();
				if (headCommit != null)
					return rw.next();
			} catch (IOException e) {
				Activator.handleError(e.getMessage(), e, true);
			} finally {
				rw.dispose();
			}
			return null;
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

	public Object execute(ExecutionEvent event) throws ExecutionException {
		Repository repository = getRepository(true, event);
		if (repository == null)
			return null;
