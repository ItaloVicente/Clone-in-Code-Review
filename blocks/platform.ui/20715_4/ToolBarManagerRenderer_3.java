		String[] vars = {
				"org.eclipse.ui.internal.services.EvaluationService.evaluate", //$NON-NLS-1$
				IServiceConstants.ACTIVE_CONTEXTS,
				IServiceConstants.ACTIVE_PART,
				IServiceConstants.ACTIVE_SELECTION,
				IServiceConstants.ACTIVE_SHELL };
		updateVariables.addAll(Arrays.asList(vars));
		context.set(UPDATE_VARS, updateVariables);
		RunAndTrack enablementUpdater = new RunAndTrack() {

			@Override
			public boolean changed(IEclipseContext context) {
				System.err
						.println("updateVariables: " + updateVariables.size()); //$NON-NLS-1$
				for (String var : updateVariables) {
					context.get(var);
				}
				updateEnablement();
				return true;
			}
		};
		context.runAndTrack(enablementUpdater);
