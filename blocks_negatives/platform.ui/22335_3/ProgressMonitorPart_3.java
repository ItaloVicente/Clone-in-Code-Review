        fTaskName = name;
        updateLabel();
        if (totalWork == IProgressMonitor.UNKNOWN || totalWork == 0) {
            fProgressIndicator.beginAnimatedTask();
        } else {
            fProgressIndicator.beginTask(totalWork);
        }
        if (fToolBar != null && !fToolBar.isDisposed()) {
        	fToolBar.setVisible(true);
        	fToolBar.setFocus();
        }
    }

    /**
     * Implements <code>IProgressMonitor.done</code>.
     * @see IProgressMonitor#done()
     */
    @Override
