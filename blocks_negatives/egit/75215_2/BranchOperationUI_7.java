					if (restore) {
						final BranchProjectTracker tracker = new BranchProjectTracker(
								repository);
						final AtomicReference<IMemento> memento = new AtomicReference<>();
						bop.addPreExecuteTask(new PreExecuteTask() {

							@Override
							public void preExecute(Repository pRepo,
									IProgressMonitor pMonitor)
									throws CoreException {
								memento.set(tracker.snapshot());
							}
						});
						bop.addPostExecuteTask(new PostExecuteTask() {

							@Override
							public void postExecute(Repository pRepo,
									IProgressMonitor pMonitor)
									throws CoreException {
								IMemento snapshot = memento.get();
								if (snapshot == null)
									return;
								tracker.save(snapshot).restore(pMonitor);
							}
						});
					}

