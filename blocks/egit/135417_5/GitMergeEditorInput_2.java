					if (file != null) {
						left = SaveableCompareEditorInput
								.createFileElement(file);
					} else {
						left = new LocalNonWorkspaceTypedElement(repository,
								location);
					}
