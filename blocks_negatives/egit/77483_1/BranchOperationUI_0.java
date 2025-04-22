			final AtomicReference<IMemento> memento = new AtomicReference<>();
			bop.addPreExecuteTask(new PreExecuteTask() {

				@Override
				public void preExecute(Repository pRepo,
						IProgressMonitor pMonitor) throws CoreException {
					memento.set(tracker.snapshot());
				}
			});
			bop.addPostExecuteTask(new PostExecuteTask() {
