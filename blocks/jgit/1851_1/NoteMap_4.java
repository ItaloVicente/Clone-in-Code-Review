	public void set(AnyObjectId noteOn
		InMemoryNoteBucket newRoot = root.set(noteOn
		if (newRoot == null) {
			newRoot = new LeafBucket(0);
			newRoot.nonNotes = root.nonNotes;
		}
		root = newRoot;
	}

	public void set(AnyObjectId noteOn
			throws IOException {
		ObjectId dataId;
		if (noteData != null) {
			byte[] dataUTF8 = Constants.encode(noteData);
			dataId = ins.insert(Constants.OBJ_BLOB
		} else {
			dataId = null;
		}
		set(noteOn
	}

	public void remove(AnyObjectId noteOn) throws IOException {
		set(noteOn
	}

