			ObjectId id;
			if (insert) {
				id = b.writeTree(inserter);
			} else {
				id = b.getTreeId();
			}
			fmt.append(nameBuf
