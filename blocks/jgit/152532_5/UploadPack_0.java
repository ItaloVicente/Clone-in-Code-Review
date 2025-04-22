	private static List<Ref> moreImportantRefsFirst(
			Collection<Ref> visibleRefs) {
		Predicate<Ref> startsWithRefsHeads = ref -> ref.getName()
				.startsWith(Constants.R_HEADS);
		Predicate<Ref> startsWithRefsTags = ref -> ref.getName()
				.startsWith(Constants.R_TAGS);
		Predicate<Ref> allOther = ref -> !startsWithRefsHeads.test(ref)
				&& !startsWithRefsTags.test(ref);

		List<Ref> sorted = new ArrayList<>(visibleRefs.size());
		sorted.addAll(filterRefByPredicate(visibleRefs
		sorted.addAll(filterRefByPredicate(visibleRefs
		sorted.addAll(filterRefByPredicate(visibleRefs

		return sorted;
	}

	private static List<Ref> filterRefByPredicate(Collection<Ref> refs
			Predicate<Ref> predicate) {
		return refs.stream().filter(predicate).collect(Collectors.toList());
	}

	private static List<RevCommit> refsToRevCommits(RevWalk walk
			List<Ref> refs) throws MissingObjectException
		List<ObjectId> objIds = refs.stream().map(
				ref -> firstNonNull(ref.getPeeledObjectId()
				.collect(Collectors.toList());
		return objectIdsToRevCommits(walk
	}

	private static ObjectId firstNonNull(ObjectId one
		return one != null ? one : two;
	}

