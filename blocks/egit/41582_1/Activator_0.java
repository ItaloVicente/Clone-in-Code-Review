		if (hasJavaPlugin()) {
			final ContextTypeRegistry codeTemplateContextRegistry = JavaPlugin
					.getDefault().getCodeTemplateContextRegistry();
			final Iterator<?> ctIter = codeTemplateContextRegistry
					.contextTypes();

			while (ctIter.hasNext()) {
				final TemplateContextType contextType = (TemplateContextType) ctIter
						.next();
				contextType.addResolver(new GitTemplateVariableResolver());
			}
