		IProject project = null;
		if (Activator.hasJavaPlugin()) {
			if (context instanceof CodeTemplateContext) {
				IJavaProject javaProject = ((CodeTemplateContext) context)
						.getJavaProject();
				if (javaProject != null) {
					project = javaProject.getProject();
