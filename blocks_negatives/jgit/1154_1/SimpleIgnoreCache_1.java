	 *	Adds a node located at the FileTreeIterator's root directory.
	 *	<br>
	 *	Will check for the presence of a .gitignore using {@link FileTreeIterator#hasGitIgnore()}.
	 *	If no .gitignore file exists, nothing will be done.
	 *  <br>
	 *  Will check the last time of modification using {@link FileTreeIterator#hasGitIgnore()}.
	 *  If a node already exists and the time stamp has not changed, do nothing.
	 *	<br>
	 *  Note: This can be extended later if necessary to AbstractTreeIterator by using
	 *  byte[] path instead of File directory.
