		long id1 = ((MarkerSupportItem) item1).getID();
		long id2 = ((MarkerSupportItem) item2).getID();
		if (id1 < id2) {
			return -1;
		}
		if (id1 > id2) {
			return 1;
		}
		return 0;
