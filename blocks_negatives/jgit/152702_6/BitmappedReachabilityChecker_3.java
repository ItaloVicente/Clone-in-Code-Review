		Iterator<RevCommit> it = starters.iterator();
		while (it.hasNext()) {
			BitmapBuilder starterBitmap = calculator.getBitmap(it.next(),
					NullProgressMonitor.INSTANCE);
			remainingTargets.removeIf(starterBitmap::contains);
			if (remainingTargets.isEmpty()) {
				return Optional.empty();
