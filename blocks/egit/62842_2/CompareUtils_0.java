				Assert.isNotNull(file);
				IPath location = file.getLocation();
				Assert.isNotNull(location);

				final ITypedElement base;
				if (Files.isSymbolicLink(location.toFile().toPath())) {
					base = new LocalNonWorkspaceTypedElement(repository,
							location);
				} else {
					base = SaveableCompareEditorInput.createFileElement(file);
				}
