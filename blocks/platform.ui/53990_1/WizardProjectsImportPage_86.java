							.subTask(DataTransferMessages.WizardProjectsImportPage_ProcessingMessage);
					while (filesIterator1.hasNext()) {
						selectedProjects[index1++] = (ProjectRecord) filesIterator1
								.next();
					}
				} else if (!dirSelected
						&& ArchiveFileManipulations.isZipFile(path)) {
					ZipFile sourceFile = getSpecifiedZipSourceFile(path);
					if (sourceFile == null) {
						return;
					}
					structureProvider = new ZipLeveledStructureProvider(
							sourceFile);
					Object child2 = structureProvider.getRoot();
