	/**
	 * Get the old name associated with this file.
	 * <p>
	 * The meaning of the old name can differ depending on the semantic meaning
	 * of this patch:
	 * <ul>
	 * <li><i>file add</i>: always <code>/dev/null</code></li>
	 * <li><i>file modify</i>: always {@link #getNewName()}</li>
	 * <li><i>file delete</i>: always the file being deleted</li>
	 * <li><i>file copy</i>: source file the copy originates from</li>
	 * <li><i>file rename</i>: source file the rename originates from</li>
	 * </ul>
	 *
	 * @return old name for this file.
	 */
	public String getOldName() {
		return oldName;
	}

	/**
	 * Get the new name associated with this file.
	 * <p>
	 * The meaning of the new name can differ depending on the semantic meaning
	 * of this patch:
	 * <ul>
	 * <li><i>file add</i>: always the file being created</li>
	 * <li><i>file modify</i>: always {@link #getOldName()}</li>
	 * <li><i>file delete</i>: always <code>/dev/null</code></li>
	 * <li><i>file copy</i>: destination file the copy ends up at</li>
	 * <li><i>file rename</i>: destination file the rename ends up at/li>
	 * </ul>
	 *
	 * @return new name for this file.
	 */
	public String getNewName() {
		return newName;
	}

	/** @return the old file mode, if described in the patch */
	public FileMode getOldMode() {
		return oldMode;
	}

	/** @return the new file mode, if described in the patch */
	public FileMode getNewMode() {
		return newMode;
	}

	/** @return the type of change this patch makes on {@link #getNewName()} */
	public ChangeType getChangeType() {
		return changeType;
	}

	/**
	 * @return similarity score between {@link #getOldName()} and
	 *         {@link #getNewName()} if {@link #getChangeType()} is
	 *         {@link ChangeType#COPY} or {@link ChangeType#RENAME}.
	 */
	public int getScore() {
		return score;
	}

	/**
	 * Get the old object id from the <code>index</code>.
	 *
	 * @return the object id; null if there is no index line
	 */
	public AbbreviatedObjectId getOldId() {
		return oldId;
	}

	/**
	 * Get the new object id from the <code>index</code>.
	 *
	 * @return the object id; null if there is no index line
	 */
	public AbbreviatedObjectId getNewId() {
		return newId;
	}

