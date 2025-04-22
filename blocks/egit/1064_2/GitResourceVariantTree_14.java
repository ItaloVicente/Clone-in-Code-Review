			TreeEntry[] members = treeEntry.members();

			for (TreeEntry entry : members) {
				if (entry instanceof FileTreeEntry) {
					FileTreeEntry fileTreeEntry = (FileTreeEntry) entry;
					GitFile file = new GitFile(container, fileTreeEntry);
					result.add(new GitBlobResourceVariant(file, fileTreeEntry));
				} else if (entry instanceof Tree) {
					Tree tree = (Tree) entry;
					GitFolder gitFolder = new GitFolder(container, tree);
					result.add(new GitFolderResourceVariant(gitFolder));
