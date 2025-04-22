			try {
				Set<WorkingTreeModifiedEvent> events;
				synchronized (repositoriesChanged) {
					if (repositoriesChanged.isEmpty()) {
						return Status.OK_STATUS;
					}
					events = new LinkedHashSet<>(repositoriesChanged.values());
					repositoriesChanged.clear();
