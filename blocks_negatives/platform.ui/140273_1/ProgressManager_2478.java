				 * Only report severe errors to the StatusManager if the error
				 * is not part of a job group, or if the job is the last job in
				 * a job group. For job groups, the JobManager accumulates the
				 * status of jobs belonging to the group, suppresses the status
				 * reporting of the individual jobs and reports a single
				 * MultiStatus for the group, so mirror that behavior here.
