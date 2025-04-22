        if (perspectiveId != null) {
            try {
                page = PlatformUI.getWorkbench().showPerspective(perspectiveId,
                        workbenchWindow);
            } catch (WorkbenchException e) {
                IDEWorkbenchPlugin
