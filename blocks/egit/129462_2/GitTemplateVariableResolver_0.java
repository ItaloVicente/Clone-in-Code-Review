		IProject project = Platform.getAdapterManager().getAdapter(context,
				IProject.class);
		if (project != null) {
			return project;
		}
		if (NO_ADAPTABLE_SUPPORT && Activator.hasJavaPlugin()) {
			boolean hasPublicMethod = context.getClass().getSimpleName()
					.equals("CodeTemplateContext"); //$NON-NLS-1$
			Method method;
			try {
				if (hasPublicMethod) {
					method = context.getClass().getMethod("getJavaProject"); //$NON-NLS-1$
				} else {
					method = context.getClass().getSuperclass()
							.getDeclaredMethod("getJavaProject"); //$NON-NLS-1$
					method.setAccessible(true);
