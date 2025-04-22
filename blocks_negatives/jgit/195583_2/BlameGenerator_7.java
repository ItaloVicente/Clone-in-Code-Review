	/**
	 * Push a candidate object onto the generator's traversal stack.
	 * <p>
	 * Candidates should be pushed in history order from oldest-to-newest.
	 * Applications should push the starting commit first, then the index
	 * revision (if the index is interesting), and finally the working tree copy
	 * (if the working tree is interesting).
	 *
	 * @param blameCommit
	 *            ordered commits to use instead of RevWalk.
	 * @return {@code this}
	 * @throws java.io.IOException
	 *             the repository cannot be read.
	 * @since 6.3
	 */
	public BlameGenerator push(RevCommit blameCommit) throws IOException {
		if (!find(blameCommit, resultPath)) {
			return this;
		}

		Candidate c = new Candidate(getRepository(), blameCommit, resultPath);
		c.sourceBlob = idBuf.toObjectId();
		c.loadText(reader);
		c.regionList = new Region(0, 0, c.sourceText.size());
		remaining = c.sourceText.size();
		push(c);
		return this;
	}

