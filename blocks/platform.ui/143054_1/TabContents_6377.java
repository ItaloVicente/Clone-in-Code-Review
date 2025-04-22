					throws Exception {
					section.aboutToBeHidden();
				}
			};
			SafeRunnable.run(runnable);
		}
	}

	public void setInput(final IWorkbenchPart part, final ISelection selection) {
		for (final ISection section : sections) {
			ISafeRunnable runnable = new SafeRunnable() {

				@Override
