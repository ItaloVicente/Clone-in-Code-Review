	public static void packRefs(ProgressMonitor pm
			throws IOException {
		Set<Entry<String
		pm.beginTask("pack refs"
		Collection<RefDirectoryUpdate> updates = new LinkedList<RefDirectoryUpdate>();
		for (Map.Entry<String
			Ref ref = entry.getValue();
			if (!ref.isSymbolic() && ref.getStorage().isLoose()) {
				updates.add(new RefDirectoryUpdate((RefDirectory) repo
						.getRefDatabase()
			}
			pm.update(1);
		}
		((RefDirectory) repo.getRefDatabase()).pack(updates);
	}

