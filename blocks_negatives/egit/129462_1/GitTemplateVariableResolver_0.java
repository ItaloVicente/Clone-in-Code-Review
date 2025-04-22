		IProject project = null;
		if (Activator.hasJavaPlugin()) {
			if (context instanceof CodeTemplateContext) {
				IJavaProject javaProject = ((CodeTemplateContext) context)
						.getJavaProject();
				if (javaProject != null) {
					project = javaProject.getProject();
				}
			} else if (context instanceof CompilationUnitContext) {
				ICompilationUnit cu = ((CompilationUnitContext) context)
						.getCompilationUnit();
				if (cu != null) {
					project = cu.getJavaProject().getProject();
