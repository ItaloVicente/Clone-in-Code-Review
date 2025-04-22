					Display.getDefault().syncExec(new Runnable() {
						@Override
						public void run() {
							Job.getJobManager().beginRule(rule, null);
							lockAcquired[0] = true;
							Job.getJobManager().endRule(rule);
						}
