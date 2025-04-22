	/**
	 * Retrieves the {@link AttributesNode} that holds the information located
	 * in $GIT_DIR/info/attributes file.
	 *
	 * @return the {@link AttributesNode} that holds the information located in
	 *         $GIT_DIR/info/attributes file.
	 * @throws IOException
	 *             if an error is raised while parsing the attributes file
	 * @since 3.7
	 */
	public AttributesNode getInfoAttributesNode() throws IOException {
		if (infoAttributeNode instanceof InfoAttributesNode)
			infoAttributeNode = ((InfoAttributesNode) infoAttributeNode).load();
		return infoAttributeNode;
	}

	/**
	 * Retrieves the {@link AttributesNode} that holds the information located
	 * in system-wide file.
	 *
	 * @return the {@link AttributesNode} that holds the information located in
	 *         system-wide file.
	 * @throws IOException
	 *             IOException if an error is raised while parsing the
	 *             attributes file
	 * @see CoreConfig#getAttributesFile()
	 * @since 3.7
	 */
	public AttributesNode getGlobalAttributesNode() throws IOException {
		if (globalAttributeNode instanceof GlobalAttributesNode)
			globalAttributeNode = ((GlobalAttributesNode) globalAttributeNode)
					.load();
		return globalAttributeNode;
	}

