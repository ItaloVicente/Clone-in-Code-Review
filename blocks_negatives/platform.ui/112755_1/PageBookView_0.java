		Map clone = (Map) ((HashMap) mapPartToRec).clone();
		Iterator itr = clone.values().iterator();
		while (itr.hasNext()) {
			PageRec rec = (PageRec) itr.next();
			removePage(rec, true);
		}
