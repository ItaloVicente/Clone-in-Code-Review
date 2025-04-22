				try {
					final ContextTypeRegistry codeTemplateContextRegistry = JavaPlugin
							.getDefault().getCodeTemplateContextRegistry();
					final Iterator<?> ctIter = codeTemplateContextRegistry
							.contextTypes();

					while (ctIter.hasNext()) {
						final TemplateContextType contextType = (TemplateContextType) ctIter
								.next();
						contextType.addResolver(new GitTemplateVariableResolver(
								"git_config", //$NON-NLS-1$
								UIText.GitTemplateVariableResolver_GitConfigDescription));
					}
				} catch (Throwable e) {
					logError("Cannot register git support for Java templates", //$NON-NLS-1$
							e);
