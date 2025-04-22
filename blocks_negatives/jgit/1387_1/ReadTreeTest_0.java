		for (java.util.Map.Entry<String, String> e : headEntries.entrySet()) {
			fileEntry = tree.addFile(e.getKey());
			fileEntry.setId(genSha1(e.getValue()));
			parent = fileEntry.getParent();
			while (parent != null) {
				parent.setId(ow.writeTree(parent));
				parent = parent.getParent();
