			List<DiffEntry> renames = Collections.emptyList();
			try {
				renames = detector.compute(walk.getObjectReader(),
						NullProgressMonitor.INSTANCE);
			} catch (CancelledException e) {
				return null;
			}
