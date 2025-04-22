				return resolveGrandparentFile(gitExe);
		}
		return null;
	}

	private static File resolveGrandparentFile(File grandchild) {
		if (grandchild != null) {
			File parent = grandchild.getParentFile();
			if (parent != null)
				return parent.getParentFile();
