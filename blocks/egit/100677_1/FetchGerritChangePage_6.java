			@Override
			protected void canceling() {
				super.canceling();
				if (changeList != null) {
					changeList.cancel(ChangeList.CancelMode.INTERRUPT);
				}
			}

			private Change completeChange(Change originalChange,
					IProgressMonitor monitor)
					throws OperationCanceledException {
				if (changeList != null) {
					monitor.subTask(NLS.bind(
							UIText.FetchGerritChangePage_FetchingRemoteRefsMessage,
							uri));
					Collection<Change> changes;
					try {
						changes = changeList.get();
					} catch (InvocationTargetException
							| InterruptedException e) {
						throw new OperationCanceledException();
					}
					if (monitor.isCanceled()) {
						throw new OperationCanceledException();
					}
					return findHighestPatchSet(changes,
							originalChange.getChangeNumber().intValue());
				}
				return originalChange;
			}

