		ObjectInserter oi = db.newObjectInserter();
		try {
			for (java.util.Map.Entry<String
				fileEntry = tree.addFile(e.getKey());
				fileEntry.setId(genSha1(e.getValue()));
				parent = fileEntry.getParent();
				while (parent != null) {
					parent.setId(oi.insert(Constants.OBJ_TREE
					parent = parent.getParent();
				}
