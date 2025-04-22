				IStructuredSelection sel = null;
				if (selection != null) {
					sel = new StructuredSelection(selection);
				} else {
					sel = new StructuredSelection();
				}
				importWizard.init(PlatformUI.getWorkbench(), sel);
