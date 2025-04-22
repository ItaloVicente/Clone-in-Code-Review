		public Change complete(CancelableFuture<Collection<Change>> list,
				String uri, IProgressMonitor monitor) {
			if (!isComplete()) {
				monitor.subTask(MessageFormat.format(
						UIText.AsynchronousRefProposalProvider_FetchingRemoteRefsMessage,
						uri));
				Collection<Change> changes;
				try {
					changes = list.get();
				} catch (InvocationTargetException | InterruptedException e) {
					throw new OperationCanceledException();
				}
				if (monitor.isCanceled()) {
					throw new OperationCanceledException();
				}
				Change highest = findHighestPatchSet(changes,
						getChangeNumber());
				if (highest != null) {
					return highest;
				}
			}
			return this;
