	private String oldFilePath;
	private String newFilePath;
	private String changeType;
	private int linesAdded;
	private int linesDeleted;
	private String diffText;

	public TextualDiff(final String oldFilePath
			final int linesAdded
		this.oldFilePath = checkNotEmpty("oldFilePath"
		this.newFilePath = checkNotEmpty("newFilePath"
		this.changeType = checkNotEmpty("changeType"

		this.linesAdded = linesAdded;
		this.linesDeleted = linesDeleted;

		this.diffText = checkNotEmpty("diffText"
	}

	public String getOldFilePath() {
		return oldFilePath;
	}

	public String getNewFilePath() {
		return newFilePath;
	}

	public String getChangeType() {
		return changeType;
	}

	public int getLinesAdded() {
		return linesAdded;
	}

	public int getLinesDeleted() {
		return linesDeleted;
	}

	public String getDiffText() {
		return diffText;
	}
