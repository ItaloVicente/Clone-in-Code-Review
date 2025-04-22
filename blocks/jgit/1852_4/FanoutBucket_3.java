	private static final byte[] hexchar = { '0'
			'7'

	@Override
	ObjectId writeTree(ObjectInserter inserter) throws IOException {
		byte[] nameBuf = new byte[2];
		TreeFormatter fmt = new TreeFormatter(treeSize());
		NonNoteEntry e = nonNotes;

		for (int cell = 0; cell < 256; cell++) {
			NoteBucket b = table[cell];
			if (b == null)
				continue;

			nameBuf[0] = hexchar[cell >>> 4];
			nameBuf[1] = hexchar[cell & 0x0f];

			while (e != null && e.pathCompare(nameBuf
				e.format(fmt);
				e = e.next;
			}

			fmt.append(nameBuf
		}

		for (; e != null; e = e.next)
			e.format(fmt);
		return fmt.insert(inserter);
	}

	private int treeSize() {
		int sz = cnt * TreeFormatter.entrySize(TREE
		for (NonNoteEntry e = nonNotes; e != null; e = e.next)
			sz += e.treeEntrySize();
		return sz;
	}

