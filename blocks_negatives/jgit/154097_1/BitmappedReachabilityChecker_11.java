		for (RevCommit starter : starters) {
			BitmapBuilder starterBitmap = calculator.getBitmap(starter,
					NullProgressMonitor.INSTANCE);
			remainingTargets.removeIf(starterBitmap::contains);
			if (remainingTargets.isEmpty()) {
				return Optional.empty();
