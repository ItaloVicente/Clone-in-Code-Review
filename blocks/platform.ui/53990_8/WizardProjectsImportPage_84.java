			getContainer().run(true, true, monitor -> {

				monitor
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
