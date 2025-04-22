
		Entry e = repository.getIndex().getEntry(resRelPath);
		if (e != null && e.getStage() == 0
				&& e.isModified(repository.getWorkTree())) {
			GitIndex index = repository.getIndex();
			index.checkoutEntry(repository.getWorkTree(), e);
			modifiedIndexes.add(index);
		}
