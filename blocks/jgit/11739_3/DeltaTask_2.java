		synchronized Slice stealWork() {
			DeltaTask maxTask = null;
			int maxWork = 0;
			for (DeltaTask task : tasks) {
				int r = task.remaining();
				if (r > maxWork) {
					maxTask = task;
					maxWork = r;
				}
			}
			return maxTask != null ? maxTask.stealWork() : null;
		}
	}
