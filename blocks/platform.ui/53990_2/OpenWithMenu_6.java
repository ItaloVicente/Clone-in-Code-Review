        Listener listener = event -> {
		    switch (event.type) {
		    case SWT.Selection:
		        if (menuItem.getSelection()) {
		            IDE.setDefaultEditor(file, null);
		            try {
		                openEditor(IDE.getEditorDescriptor(file), false);
		            } catch (PartInitException e) {
		                DialogUtil.openError(page.getWorkbenchWindow()
		                        .getShell(), IDEWorkbenchMessages.OpenWithMenu_dialogTitle,
		                        e.getMessage(), e);
		            }
		        }
		        break;
		    }
		};
