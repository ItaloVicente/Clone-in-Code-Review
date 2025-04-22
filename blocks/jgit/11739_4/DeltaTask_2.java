		synchronized Slice stealWork() {
			for (;;) {
				DeltaTask maxTask = null;
				int maxWork = 0;
				for (DeltaTask task : tasks) {
					int r = task.remaining();
					if (maxWork < r) {
						maxTask = task;
						maxWork = r;
					}
				}
				if (maxTask == null)
					return null;
				Slice s = maxTask.stealWork();
				if (s != null)
					return s;
			}
		}
	}
