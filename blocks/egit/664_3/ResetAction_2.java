					return Status.OK_STATUS;
				}
			};
			job.setRule(operation.getSchedulingRule());
			job.setUser(true);
			job.schedule();
