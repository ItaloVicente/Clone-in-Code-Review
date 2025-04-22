            }
            return Status.OK_STATUS;
        }

    }

    /**
     * Create a new instance of the receiver with a site of partSite
     *
     * @param partSite
     *            PartSite.
     */
    public WorkbenchSiteProgressService(final PartSite partSite) {
        site = partSite;
        updateJob = new SiteUpdateJob();
        updateJob.setSystem(true);
    }

    /**
     * Dispose the resources allocated by the receiver.
     *
     */
    public void dispose() {
        if (updateJob != null) {
