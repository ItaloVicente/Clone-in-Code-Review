		if (gitValue != null) {
			variable.setValue(gitValue);
		}
	}

	public static IProject getProject(TemplateContext context) {
		IProject project = null;
		if (context instanceof CodeTemplateContext) {
			project = ((CodeTemplateContext) context).getJavaProject()
					.getProject();
