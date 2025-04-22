			synchronized (runnableMonitors) {
				JobInfo info = getJobInfo(job);
				info.clearTaskInfo();
				info.clearChildren();
				runnableMonitors.remove(job);
			}
