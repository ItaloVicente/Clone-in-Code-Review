		if (!scheduledUpdates.containsKey(info)) {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					scheduledUpdates.remove(info);
					GroupInfo group = info.getGroupInfo();
					if (group != null) {
						refreshGroup(group);
					}

					if (shouldDisplay(info.getJob(), showSystemJobs)) {
						for (IJobProgressManagerListener listener : listeners) {
							listener.refreshJobInfo(info);
						}
					}
				}
			};
			scheduledUpdates.put(info, runnable);
			display.asyncExec(runnable);
		}
