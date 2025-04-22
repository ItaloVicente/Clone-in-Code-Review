	private final Git git;
	private final ObjectId oldRef;
	private final ObjectId newRef;

	public ListDiffs(final Git git
		this.git = git;
		this.oldRef = oldRef;
		this.newRef = newRef;
	}

	public List<DiffEntry> execute() {
		if (newRef == null || git.getRepository() == null) {
			return emptyList();
		}

		try (final ObjectReader reader = git.getRepository().newObjectReader()) {
			CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
			if (oldRef != null) {
				oldTreeIter.reset(reader
			}
			CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
			newTreeIter.reset(reader
			return new CustomDiffCommand(git).setNewTree(newTreeIter).setOldTree(oldTreeIter)
					.setShowNameAndStatusOnly(true).call();
		} catch (final Exception ex) {
			throw new RuntimeException(ex);
		}
	}
