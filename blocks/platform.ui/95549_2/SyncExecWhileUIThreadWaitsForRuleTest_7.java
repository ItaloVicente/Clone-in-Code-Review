					Display.getDefault().asyncExec(() -> {
						blocked[0] = true;
						try {
							Job.getJobManager().beginRule(rule, beginRuleMonitor);
						} finally {
							Job.getJobManager().endRule(rule);
