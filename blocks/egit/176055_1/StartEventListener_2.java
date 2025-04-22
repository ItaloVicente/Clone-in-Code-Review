
	private void registerTemplateVariableResolvers() {
		if (!Activator.hasJavaPlugin()) {
			return;
		}
		runAsync(() -> {
			try {
				ContextTypeRegistry codeTemplateContextRegistry = org.eclipse.jdt.internal.ui.JavaPlugin
						.getDefault().getCodeTemplateContextRegistry();
				Iterator<?> ctIter = codeTemplateContextRegistry.contextTypes();

				while (ctIter.hasNext()) {
					TemplateContextType contextType = (TemplateContextType) ctIter
							.next();
					contextType.addResolver(new GitTemplateVariableResolver(
							"git_config", //$NON-NLS-1$
							UIText.GitTemplateVariableResolver_GitConfigDescription));
				}
			} catch (Throwable e) {
				Activator.logError(
						"Cannot register git support for Java templates", //$NON-NLS-1$
						e);
			}
		});
	}
