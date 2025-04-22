				StatusAdapter statusAdapter = null;
				if (event.getJobGroupResult() != null
						&& event.getJobGroupResult().getSeverity() == IStatus.ERROR) {
					statusAdapter = new StatusAdapter(event.getJobGroupResult());
				} else if (event.getResult() != null
						&& event.getResult().getSeverity() == IStatus.ERROR
						&& (event.getJob() == null || event.getJob().getJobGroup() == null)) {
					statusAdapter = new StatusAdapter(event.getResult());
