		}
		int workers = repositories.length;
		String taskName = NLS.bind(CoreText.PullOperation_TaskName,
				Integer.valueOf(workers));

		int maxThreads = Runtime.getRuntime().availableProcessors();
		JobGroup jobGroup = new JobGroup(taskName, maxThreads, workers);

		SubMonitor progress = SubMonitor.convert(m, workers);
		for (Repository repository : repositories) {
			PullJob pullJob = new PullJob(repository, configs.get(repository));
			pullJob.setJobGroup(jobGroup);
			pullJob.schedule();
		}
		long noTimeouut = 0;
		try {
			jobGroup.join(noTimeouut, progress);
		} catch (OperationCanceledException | InterruptedException e) {
			throw new CoreException(Status.CANCEL_STATUS);
		}
