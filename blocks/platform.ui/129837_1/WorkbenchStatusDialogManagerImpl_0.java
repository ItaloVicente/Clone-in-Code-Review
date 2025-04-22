
	@Override
	public void finished(JobTreeElement jte) {
	}

	@Override
	public void removed(JobTreeElement jte) {
		if (jte instanceof JobInfo) {
			JobInfo jobInfo = (JobInfo) jte;
			StatusAdapter statusAdapter = StatusAdapterHelper.getInstance().getStatusAdapter(jobInfo);
			if (statusAdapter != null) {
				getErrors().remove(statusAdapter);
			}
		} else if (jte == null) {
			StatusAdapterHelper.getInstance().clear();
			getErrors().clear();
		}

	}
