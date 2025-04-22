                            final IWorkbench workbench = PlatformUI
                                    .getWorkbench();
                            IWorkbenchWindow[] windows = workbench
                                    .getWorkbenchWindows();
                            for (int i = 0; i < windows.length; i++) {
                                if (windows[i] instanceof WorkbenchWindow) {
                                    final WorkbenchWindow window = (WorkbenchWindow) windows[i];
