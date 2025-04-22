			}
			return Status.OK_STATUS;
		}

	}

	public WorkbenchSiteProgressService(final PartSite partSite) {
		site = partSite;
		updateJob = new SiteUpdateJob();
		updateJob.setSystem(true);
	}

	public void dispose() {
		if (updateJob != null) {
