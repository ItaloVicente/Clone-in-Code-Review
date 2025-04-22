			BusyIndicator.showWhile(legacyWindow.getWorkbench().getDisplay(), new Runnable() {
				@Override
				public void run() {
					try {
						if (desc.getLauncher() != null) {
							Object launcher = WorkbenchPlugin.createExtension(desc
									.getConfigurationElement(),
									IWorkbenchRegistryConstants.ATT_LAUNCHER);
							((IEditorLauncher) launcher).open(pathInput.getPath());
						} else {
							ExternalEditor oEditor = new ExternalEditor(pathInput.getPath(), desc);
							oEditor.open();
						}
					} catch (CoreException e) {
						ex[0] = e;
