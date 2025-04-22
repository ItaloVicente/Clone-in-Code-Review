	public static class CheckoutAction extends Action {

		private final Supplier<IStructuredSelection> selectionProvider;

		public CheckoutAction(
				Supplier<IStructuredSelection> selectionProvider) {
			super(UIText.CommitFileDiffViewer_CheckoutThisVersionMenuLabel);
			this.selectionProvider = selectionProvider;
		}

		@Override
		public void run() {
			DiscardChangesOperation operation = createOperation();
			Map<Repository, Collection<String>> paths = operation
					.getPathsPerRepository();
			if (!CommandConfirmation.confirmCheckout(null, paths, true)) {
				return;
			}
			JobUtil.scheduleUserWorkspaceJob(operation,
					UIText.DiscardChangesAction_discardChanges,
					JobFamilies.DISCARD_CHANGES);
		}

		private DiscardChangesOperation createOperation() {
			Collection<FileDiff> diffs = getFileDiffs(selectionProvider.get());
			if (diffs.isEmpty()) {
				return null;
			}
			FileDiff first = diffs.iterator().next();
			Repository repository = first.getRepository();
			String revision = first.getCommit().getName();
			List<String> paths = diffs.stream().map(d -> d.getNewPath())
					.collect(Collectors.toList());
			return new DiscardChangesOperation(repository, paths, revision);
		}

		private Collection<FileDiff> getFileDiffs(
				IStructuredSelection selection) {
			List<FileDiff> result = new ArrayList<>();
			for (Object obj : selection.toList()) {
				FileDiff diff = Adapters.adapt(obj, FileDiff.class);
				if (diff != null && diff.getChange() != ChangeType.DELETE
						&& !diff.isSubmodule()) {
					result.add(diff);
				}
			}
			return result;
		}
	}
