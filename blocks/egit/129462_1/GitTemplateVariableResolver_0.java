		IProject project = Platform.getAdapterManager().getAdapter(context,
				IProject.class);
		if (project != null) {
			return project;
		}
		if (NO_ADAPTABLE_SUPPORT && Activator.hasJavaPlugin()) {
			Method method;
			try {
				method = context.getClass().getMethod("getJavaProject"); //$NON-NLS-1$
				Object result = method.invoke(context);
				if (result instanceof IJavaProject) {
					IJavaProject javaProject = (IJavaProject) result;
					return javaProject.getProject();
