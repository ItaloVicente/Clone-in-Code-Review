    /**
     * The details button has been selected. Open or close the progress viewer
     * as appropriate.
     *
     */
    void handleDetailsButtonSelect() {
        Shell shell = getShell();
        Point shellSize = shell.getSize();
        Composite composite = (Composite) getDialogArea();
        if (viewer != null) {
            viewer.getControl().dispose();
            viewer = null;
            composite.layout();
            shell.setSize(shellSize.x, shellSize.y - viewerHeight);
            detailsButton.setText(ProgressMessages.ProgressMonitorJobsDialog_DetailsTitle);
        } else {
            if (progressManager.getRootElements(Policy.DEBUG_SHOW_ALL_JOBS).length == 0) {
                detailsButton.setEnabled(false);
                return;
            }

            viewer = new DetailedProgressViewer(viewerComposite, SWT.MULTI
                    | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER, progressService, finishedJobs);
            viewer.setComparator(new ViewerComparator() {
                @Override
