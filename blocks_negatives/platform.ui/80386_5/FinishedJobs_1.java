				synchronized (keptjobinfos) {
					if (element == info && !keptjobinfos.contains(tinfo)) {
						toBeRemoved = findJobsToRemove(element);
						keptjobinfos.add(tinfo);
						finishedTime.put(tinfo, new Long(System
								.currentTimeMillis()));
					}
