			boolean cancelled = false;
			List<DiffEntry> renames = Collections.emptyList();
			try {
				renames = detector.compute(walk.getObjectReader(),
						new EclipseGitProgressTransformer(
								progress.newChild(1)));
			} catch (CancelledException e) {
				cancelled = true;
			}
			if (!cancelled) {
