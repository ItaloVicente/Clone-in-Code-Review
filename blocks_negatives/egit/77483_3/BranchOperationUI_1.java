				public void postExecute(Repository pRepo,
						IProgressMonitor pMonitor) throws CoreException {
					IMemento snapshot = memento.get();
					if (snapshot != null) {
						tracker.save(snapshot).restore(pMonitor);
					}
