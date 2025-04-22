	@Override
	ObjectId writeTree(ObjectInserter inserter) throws IOException {
		byte[] nameBuf = new byte[OBJECT_ID_STRING_LENGTH];
		int nameLen = OBJECT_ID_STRING_LENGTH - prefixLen;
		TreeFormatter fmt = new TreeFormatter(treeSize(nameLen));
		NonNoteEntry e = nonNotes;

		for (int i = 0; i < cnt; i++) {
			Note n = notes[i];

			n.copyTo(nameBuf

			while (e != null
					&& e.pathCompare(nameBuf
				e.format(fmt);
				e = e.next;
			}

			fmt.append(nameBuf
		}

		for (; e != null; e = e.next)
			e.format(fmt);
		return fmt.insert(inserter);
	}

	private int treeSize(final int nameLen) {
		int sz = cnt * TreeFormatter.entrySize(REGULAR_FILE
		for (NonNoteEntry e = nonNotes; e != null; e = e.next)
			sz += e.treeEntrySize();
		return sz;
	}

