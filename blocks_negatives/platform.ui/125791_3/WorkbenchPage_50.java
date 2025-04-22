			BusyIndicator.showWhile(legacyWindow.getWorkbench().getDisplay(), () -> {
				try {
					if (desc.getLauncher() != null) {
						Object launcher = WorkbenchPlugin.createExtension(desc.getConfigurationElement(),
								IWorkbenchRegistryConstants.ATT_LAUNCHER);
						((IEditorLauncher) launcher).open(pathInput.getPath());
					} else {
						ExternalEditor oEditor = new ExternalEditor(pathInput.getPath(), desc);
						oEditor.open();
