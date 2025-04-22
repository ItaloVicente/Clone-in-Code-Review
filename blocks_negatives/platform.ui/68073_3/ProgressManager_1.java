			Iterator iterator = jobs.keySet().iterator();
			Collection result = new HashSet();
			while (iterator.hasNext()) {
				Job next = (Job) iterator.next();
				if (!isCurrentDisplaying(next, debug)) {
					JobInfo jobInfo = (JobInfo) jobs.get(next);
