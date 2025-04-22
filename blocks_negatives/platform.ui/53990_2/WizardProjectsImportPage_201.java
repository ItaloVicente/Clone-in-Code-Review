							.beginTask(
									DataTransferMessages.WizardProjectsImportPage_SearchingMessage,
									100);
					selectedProjects = new ProjectRecord[0];
					Collection files = new ArrayList();
					monitor.worked(10);
					if (!dirSelected
							&& ArchiveFileManipulations.isTarFile(path)) {
						TarFile sourceTarFile = getSpecifiedTarSourceFile(path);
						if (sourceTarFile == null) {
							return;
						}

						structureProvider = new TarLeveledStructureProvider(
								sourceTarFile);
						Object child = structureProvider.getRoot();

						if (!collectProjectFilesFromProvider(files, child, 0,
								monitor)) {
							return;
						}
						Iterator filesIterator = files.iterator();
						selectedProjects = new ProjectRecord[files.size()];
						int index = 0;
						monitor.worked(50);
						monitor
								.subTask(DataTransferMessages.WizardProjectsImportPage_ProcessingMessage);
						while (filesIterator.hasNext()) {
							selectedProjects[index++] = (ProjectRecord) filesIterator
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
						Object child = structureProvider.getRoot();
