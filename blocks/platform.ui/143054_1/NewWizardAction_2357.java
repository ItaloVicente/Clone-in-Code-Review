					if (resource != null) {
						selectionToPass = new StructuredSelection(resource);
					}
				}
			}
		}

		wizard.init(workbenchWindow.getWorkbench(), selectionToPass);

		IDialogSettings workbenchSettings = WorkbenchPlugin.getDefault().getDialogSettings();
		IDialogSettings wizardSettings = workbenchSettings.getSection("NewWizardAction"); //$NON-NLS-1$
		if (wizardSettings == null) {
