                    if (resource != null) {
                        selectionToPass = new StructuredSelection(resource);
                    }
                }
            }
        }

        wizard.init(workbenchWindow.getWorkbench(), selectionToPass);

        IDialogSettings workbenchSettings = WorkbenchPlugin.getDefault()
                .getDialogSettings();
        IDialogSettings wizardSettings = workbenchSettings
        if (wizardSettings == null) {
