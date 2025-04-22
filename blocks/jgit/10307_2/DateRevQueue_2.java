
		if (!index.isEmpty()) {
			Entry prev = null;
			for (Entry i : index) {
				if (when > i.commit.commitTime) {
					if (prev != null) {
						q = prev;
					}
					break;
				}
				prev = i;
			}
		}

