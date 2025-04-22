			@Override
			public void removeJob(JobInfo info) {
				if (keep(info)) {
					checkForDuplicates(info);
					add(info);
				}
			}
