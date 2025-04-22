			IProgressMonitor progressMonitor) throws Exception {
		int allWork = 30 + ProjectConfiguratorExtensionManager.getAllExtensionLabels().size() * 5;
		SubMonitor subMonitor = SubMonitor.convert(progressMonitor,
				NLS.bind(DataTransferMessages.SmartImportJob_inspecting,
						container.getLocation().toFile().getAbsolutePath()),
				allWork);
