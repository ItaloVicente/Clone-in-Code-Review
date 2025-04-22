	private void registerTemplateVariableResolvers() {
		final org.eclipse.jface.text.templates.ContextTypeRegistry codeTemplateContextRegistry = org.eclipse.jdt.internal.ui.JavaPlugin
				.getDefault().getCodeTemplateContextRegistry();
		final java.util.Iterator<?> ctIter = codeTemplateContextRegistry
				.contextTypes();
		while (ctIter.hasNext()) {
			final org.eclipse.jface.text.templates.TemplateContextType contextType = (org.eclipse.jface.text.templates.TemplateContextType) ctIter
					.next();
			contextType.addResolver(new GitTemplateVariableResolver());
		}
	}

