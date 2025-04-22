			List<RevObject> wantsAsObjs = objectIdsToRevObjects(walk
					notAdvertisedWants);
			List<RevCommit> wantsAsCommits = wantsAsObjs.stream()
					.filter(obj -> obj instanceof RevCommit)
					.map(obj -> (RevCommit) obj)
					.collect(Collectors.toList());
			boolean allWantsAreCommits = wantsAsObjs.size() == wantsAsCommits
					.size();
			boolean repoHasBitmaps = reader.getBitmapIndex() != null;

			if (!allWantsAreCommits) {
				if (!repoHasBitmaps) {
					RevObject nonCommit = wantsAsObjs
							.stream()
							.filter(obj -> !(obj instanceof RevCommit))
							.limit(1)
							.collect(Collectors.toList()).get(0);
					throw new WantNotValidException(nonCommit);

