		if (value == null) {
			openOther(shell, app, window, modelService, ctx, partService);
		} else {
			try {
				openView((String) value, partService);
			} catch (PartInitException e) {
				throw new ExecutionException("Part could not be initialized", e); //$NON-NLS-1$
			}
