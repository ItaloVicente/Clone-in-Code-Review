				SafeRunner.run(new SafeRunnable(IDEWorkbenchMessages.ErrorOnCloseEditors) {
					@Override
					public void run() {
						IWorkbenchWindow w = getActiveWindow();
						if (w != null) {
							List<IEditorReference> toClose = getMatchingEditors(resourceRoots, w, deletedOnly);
							if (toClose.isEmpty()) {
								return;
							}
							closeEditors(toClose, w);
						}
