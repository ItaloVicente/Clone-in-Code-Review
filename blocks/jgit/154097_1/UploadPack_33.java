	static Stream<Ref> importantRefsFirst(
			Collection<Ref> visibleRefs) {
		Predicate<Ref> startsWithRefsHeads = ref -> ref.getName()
				.startsWith(Constants.R_HEADS);
		Predicate<Ref> startsWithRefsTags = ref -> ref.getName()
				.startsWith(Constants.R_TAGS);
		Predicate<Ref> allOther = ref -> !startsWithRefsHeads.test(ref)
				&& !startsWithRefsTags.test(ref);

		return Stream.concat(
				visibleRefs.stream().filter(startsWithRefsHeads)
				Stream.concat(
						visibleRefs.stream().filter(startsWithRefsTags)
						visibleRefs.stream().filter(allOther)));
	}

	private static ObjectId refToObjectId(Ref ref) {
		return ref.getObjectId() != null ? ref.getObjectId()
				: ref.getPeeledObjectId();
	}

	@Nullable
	private static RevCommit objectIdToRevCommit(RevWalk walk
			ObjectId objectId) {
		if (objectId == null) {
			return null;
		}

		try {
			return walk.parseCommit(objectId);
		} catch (IOException e) {
			return null;
		}
	}

