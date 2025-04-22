						throw new CoreException(
								new Status(
										IStatus.ERROR,
										Activator.getPluginId(),
										UIText.LocalNonWorkspaceTypedElement_errorWritingContents,
										e));
