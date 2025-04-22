        this.toolTip = toolTip;
        firePropertyChange(IWorkbenchPart.PROP_TITLE);
    }

    /**
     * Show that this part is busy due to a Job running that it
     * is listening to.
     * @param busy boolean to indicate that the busy state has started
     *  	or ended.
     * @see org.eclipse.ui.progress.IWorkbenchSiteProgressService#showBusyForFamily(Object)
     * @since 3.0
     */
    public void showBusy(boolean busy) {
    }

    /**
     * {@inheritDoc}
     * <p>
     * It is considered bad practise to overload or extend this method.
     * Parts should call setPartName to change their part name.
     * </p>
     */
    @Override
