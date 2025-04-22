=======
	public void packRefs() throws IOException {
		Set<Entry<String
		if (pm == null)
			pm = NullProgressMonitor.INSTANCE;
		pm.beginTask("pack refs"
		try {
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
		} finally {
			pm.endTask();
		}
	}

>>>>>>> Allow to pack a set of loose and packed refs into a new packed-ref file
