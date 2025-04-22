					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							blocked[0] = true;
							try {
								Job.getJobManager().beginRule(rule, beginRuleMonitor);
							} finally {
								Job.getJobManager().endRule(rule);
							}
							blocked[0] = false;
