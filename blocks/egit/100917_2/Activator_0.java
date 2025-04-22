		private static class WorkingTreeChanges {

			private final File workTree;

			private final Set<String> modified;

			private final Set<String> deleted;

			public WorkingTreeChanges(WorkingTreeModifiedEvent event) {
				workTree = event.getRepository().getWorkTree()
						.getAbsoluteFile();
				modified = new HashSet<>(event.getModified());
				deleted = new HashSet<>(event.getDeleted());
			}

			public File getWorkTree() {
				return workTree;
			}

			public Set<String> getModified() {
				return modified;
			}

			public Set<String> getDeleted() {
				return deleted;
			}

			public boolean isEmpty() {
				return modified.isEmpty() && deleted.isEmpty();
			}

			public WorkingTreeChanges merge(WorkingTreeModifiedEvent event) {
				modified.removeAll(event.getDeleted());
				deleted.removeAll(event.getModified());
				modified.addAll(event.getModified());
				deleted.addAll(event.getDeleted());
				return this;
			}
		}

		private Map<File, WorkingTreeChanges> repositoriesChanged = new LinkedHashMap<>();
