				final ITypedElement base;
				if (Files.isSymbolicLink(location.toFile().toPath())) {
					base = new LocalNonWorkspaceTypedElement(repository,
							location);
				} else if (file instanceof IFile) {
					base = SaveableCompareEditorInput
							.createFileElement((IFile) file);
				} else {
					return Activator.createErrorStatus(
							NLS.bind(UIText.CompareUtils_wrongResourceArgument,
									location, file));
				}

				final String gitPath = mapping.getRepoRelativePath(file);
