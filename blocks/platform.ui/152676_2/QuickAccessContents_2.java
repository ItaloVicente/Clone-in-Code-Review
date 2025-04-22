					};
					job.setPriority(Job.INTERACTIVE);
					job.schedule();
					try {
						job.join(0, new NullProgressMonitor());
					} catch (Exception e) {
						WorkbenchPlugin.log(e);
