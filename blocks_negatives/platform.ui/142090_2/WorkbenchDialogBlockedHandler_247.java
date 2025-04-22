            IProgressMonitor blockingMonitor, IStatus blockingStatus,
            String blockedName) {

        nestingDepth++;
        if (outerMonitor == null) {
            outerMonitor = blockingMonitor;
            if (blockedName == null && parentShell != null) {
