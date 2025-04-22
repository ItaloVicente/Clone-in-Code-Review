
			ReachabilityChecker reachabilityChecker = repoHasBitmaps
					? new BitmappedReachabilityChecker(walk)
					: new PedestrianReachabilityChecker(true

			List<RevCommit> starters = objectIdsToRevCommits(walk
					reachableFrom);
			Optional<RevCommit> unreachable = reachabilityChecker
					.areAllReachable(wantsAsCommits
			if (unreachable.isPresent()) {
				throw new WantNotValidException(unreachable.get());
