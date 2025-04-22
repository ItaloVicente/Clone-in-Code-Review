	/**
	 * Create a new ignore node based on the given directory. The node's
	 * ignore file will be the .gitignore file in the directory (if any)
	 * Rules contained within this node will only be applied to files
	 * which are descendants of this directory.
	 *
	 * @param baseDir
	 * 			  base directory of this ignore node
	 */
	public IgnoreNode(File baseDir) {
		this.baseDir = baseDir;
