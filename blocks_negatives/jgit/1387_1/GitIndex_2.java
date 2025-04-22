			FileTreeEntry ne = new FileTreeEntry(current, e.sha1,
					Constants.encode(newName[newName.length - 1]),
					(e.mode & FileMode.EXECUTABLE_FILE.getBits()) == FileMode.EXECUTABLE_FILE.getBits());
			current.addEntry(ne);
		}
		while (!trees.isEmpty()) {
			current.setId(writer.writeTree(current));
			trees.pop();
			if (!trees.isEmpty())
				current = trees.peek();
