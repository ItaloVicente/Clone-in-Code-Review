			walk.markStart(starters.next());
			while (walk.next() != null) {
				remainingTargets.removeIf(reachedFilter::isReachable);

				if (remainingTargets.isEmpty()) {
					return Optional.empty();
				}
