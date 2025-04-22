        try {
            IExtensionDelta delta[] = event
                    .getExtensionDeltas(WorkbenchPlugin.PI_WORKBENCH);
            IExtension ext;
            IExtensionPoint extPt;
            IWorkbenchWindow[] win = PlatformUI.getWorkbench()
                    .getWorkbenchWindows();
            if (win.length == 0) {
