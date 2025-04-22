		if (part == this) {
			for (PageRec pageRec : mapPartToRec.values()) {
				if (pageRec.subActionBars != null) {
					pageRec.subActionBars.deactivate();
				}
			}
		}
