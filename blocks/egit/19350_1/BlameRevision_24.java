	public String getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	public Integer getSourceLine(int currentLine) {
		return sourceLines.get(Integer.valueOf(currentLine));
	}

	public void addSourceLine(int currentLine, int sourceLine) {
		sourceLines.put(Integer.valueOf(currentLine),
				Integer.valueOf(sourceLine));
	}

	public Diff getDiffToParent(RevCommit parentCommit) {
		if (diffToParentCommit.containsKey(parentCommit))
			return diffToParentCommit.get(parentCommit);

		Diff diff = calculateDiffToParent(parentCommit);
		diffToParentCommit.put(parentCommit, diff);
		return diff;
	}

	private Diff calculateDiffToParent(RevCommit parentCommit) {
		ObjectReader reader = repository.newObjectReader();
		try {
			DiffEntry diffEntry = CompareCoreUtils.getChangeDiffEntry(
					repository, sourcePath, commit, parentCommit, reader);
			if (diffEntry == null)
				return null;

			RawText oldText = readText(diffEntry.getOldId(), reader);
			RawText newText = readText(diffEntry.getNewId(), reader);

			StoredConfig config = repository.getConfig();
			DiffAlgorithm diffAlgorithm = DiffAlgorithm.getAlgorithm(config
					.getEnum(ConfigConstants.CONFIG_DIFF_SECTION, null,
							ConfigConstants.CONFIG_KEY_ALGORITHM,
							SupportedAlgorithm.HISTOGRAM));

			EditList editList = diffAlgorithm.diff(RawTextComparator.DEFAULT,
					oldText, newText);

			return new Diff(diffEntry.getOldPath(), oldText, newText, editList);
		} catch (IOException e) {
			return null;
		} finally {
			reader.release();
		}
	}

	private static RawText readText(AbbreviatedObjectId blobId,
			ObjectReader reader) throws IOException {
		ObjectLoader oldLoader = reader.open(blobId.toObjectId(),
				Constants.OBJ_BLOB);
		return new RawText(oldLoader.getCachedBytes());
	}

	public static class Diff {
		private final String oldPath;

		private final RawText oldText;

		private final RawText newText;

		private final EditList editList;

		private Diff(String oldPath, RawText oldText, RawText newText,
				EditList editList) {
			this.oldPath = oldPath;
			this.oldText = oldText;
			this.newText = newText;
			this.editList = editList;
		}

		public String getOldPath() {
			return oldPath;
		}

		public RawText getOldText() {
			return oldText;
		}

		public RawText getNewText() {
			return newText;
		}

		public EditList getEditList() {
			return editList;
		}
	}
