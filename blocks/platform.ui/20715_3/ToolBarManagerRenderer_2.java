		final String[] vars = {
				"org.eclipse.ui.internal.services.EvaluationService.evaluate", //$NON-NLS-1$
				IServiceConstants.ACTIVE_CONTEXTS,
				IServiceConstants.ACTIVE_PART,
				IServiceConstants.ACTIVE_SELECTION,
				IServiceConstants.ACTIVE_SHELL };
		RunAndTrack enablementUpdater = new RunAndTrack() {

			@Override
			public boolean changed(IEclipseContext context) {
				for (String var : vars) {
					context.get(var);
				}
				updateEnablement();
				return true;
			}
		};
		context.runAndTrack(enablementUpdater);
