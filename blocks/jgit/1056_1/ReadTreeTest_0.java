		FileTreeEntry fileEntry;
		Tree parent;
		for (java.util.Map.Entry<String
			fileEntry = tree.addFile(e.getKey());
			fileEntry.setId(genSha1(e.getValue()));
			parent = fileEntry.getParent();
			while (parent != null) {
				parent.setId(ow.writeTree(parent));
				parent = parent.getParent();
			}
