				throw new CoreException(
						new Status(
								IStatus.ERROR,
								Activator.getPluginId(),
								NLS.bind(
										UIText.GitVariableResolver_VariableReferencesNonExistentResource,
										argument)));
