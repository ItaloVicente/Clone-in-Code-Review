	/** File name of the old (pre-image). */
	private String oldName;

	/** File name of the new (post-image). */
	private String newName;

	/** Old mode of the file, if described by the patch, else null. */
	private FileMode oldMode;

	/** New mode of the file, if described by the patch, else null. */
	protected FileMode newMode;

	/** General type of change indicated by the patch. */
	protected ChangeType changeType;

	/** Similarity score if {@link #changeType} is a copy or rename. */
	private int score;

	/** ObjectId listed on the index line for the old (pre-image) */
	private AbbreviatedObjectId oldId;

	/** ObjectId listed on the index line for the new (post-image) */
	protected AbbreviatedObjectId newId;

