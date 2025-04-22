			Iterator iterator = jobs.keySet().iterator();
			Collection result = new ArrayList();
			while (iterator.hasNext()) {
				Job next = (Job) iterator.next();
				if (!isCurrentDisplaying(next, debug)) {
					result.add(jobs.get(next));
