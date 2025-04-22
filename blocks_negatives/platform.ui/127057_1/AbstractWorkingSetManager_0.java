				@Override
				public IStatus runInUIThread(IProgressMonitor monitor) {
					synchronized (updaters) {
						for (WorkingSetDescriptor descriptor : descriptors) {
							List workingSets = getWorkingSetsForId(descriptor
									.getId());
							if (workingSets.isEmpty()) {
								continue;
							}
							final IWorkingSetUpdater updater = getUpdater(descriptor);
							for (Iterator iter = workingSets.iterator(); iter
									.hasNext();) {
								final IWorkingSet workingSet = (IWorkingSet) iter
										.next();
								SafeRunner.run(new WorkingSetRunnable() {

									@Override
									public void run() throws Exception {
										if (!updater.contains(workingSet)) {
											updater.add(workingSet);
										}
