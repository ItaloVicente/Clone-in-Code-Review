                IStructuredSelection selection = (IStructuredSelection) viewer
                        .getSelection();
                if (!selection.isEmpty()) {
                    IPropertySheetEntry entry = (IPropertySheetEntry) selection
                            .getFirstElement();
                    Object helpContextId = entry.getHelpContextIds();
                    if (helpContextId != null) {
                    	IWorkbenchHelpSystem helpSystem = PlatformUI.getWorkbench().getHelpSystem();

                        if (helpContextId instanceof String) {
                            helpSystem.displayHelp((String) helpContextId);
                            return;
                        }

                        Object context= getFirstContext(helpContextId, e);
                        if (context instanceof IContext) {
