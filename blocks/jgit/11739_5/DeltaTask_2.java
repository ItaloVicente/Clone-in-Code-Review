		synchronized Slice stealWork() {
			for (int attempts = 0; attempts < 2; attempts++) {
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
			return null;
		}
	}
