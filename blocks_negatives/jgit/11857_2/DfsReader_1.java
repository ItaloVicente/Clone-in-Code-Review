		if (list.isEmpty())
			return;

		switch (list.get(0).getType()) {
		case OBJ_TREE:
		case OBJ_BLOB:
			Collections.sort(list, WRITE_SORT);
		}
