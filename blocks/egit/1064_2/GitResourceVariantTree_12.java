	private void readAllMembers(IContainer parent, TreeEntry[] tree)
			throws TeamException, IOException {
		for (int i = 0; i < tree.length; i++) {
			if (tree[i] instanceof Tree) {
				Tree nestedTree = (Tree) tree[i];
				GitFolder folder = new GitFolder(parent, nestedTree);
				TreeEntry[] nestedTreeEntrys = nestedTree.members();

				readAllMembers(folder, nestedTreeEntrys);
			} else if (tree[i] instanceof FileTreeEntry) {
				FileTreeEntry fileEntry = (FileTreeEntry) tree[i];
				GitFile file = new GitFile(parent, fileEntry);

				store.setBytes(file, fileEntry.openReader().getBytes());
			} // what we should do with GitLinkTreEntry and SymlinkTreeEntry?
		}
