			@Override
			public IStatus runInUIThread(IProgressMonitor monitor) {
				Set<Entry<WorkingSetDescriptor, List<IWorkingSet>>> entrySet = nonEmptyDescriptors.entrySet();
				synchronized (updaters) {
					for (Entry<WorkingSetDescriptor, List<IWorkingSet>> e : entrySet) {
						final IWorkingSetUpdater updater = getUpdater(e.getKey());
						if (updater == NULL_UPDATER) {
							continue;
						}
						for (IWorkingSet workingSet : e.getValue()) {
							SafeRunner.run(new WorkingSetRunnable() {
