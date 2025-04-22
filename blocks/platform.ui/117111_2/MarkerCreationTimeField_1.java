		long time1 = ((MarkerSupportItem) item1).getCreationTime();
		long time2 = ((MarkerSupportItem) item2).getCreationTime();
		if (time1 < time2) {
			return -1;
		}
		if (time1 > time2) {
			return 1;
		}
		return 0;
