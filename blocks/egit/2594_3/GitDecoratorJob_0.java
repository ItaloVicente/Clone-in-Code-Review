			try {
				GitLightweightDecorator.processDecoration(elements);
			} catch (Exception e) {
				if (GitTraceLocation.DECORATION.isActive())
					GitTraceLocation.getTrace().trace(
							GitTraceLocation.DECORATION.getLocation(),
							"An error occurred during resource decoration", e); //$NON-NLS-1$
			}
