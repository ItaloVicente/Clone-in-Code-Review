			toPrune.add(pack.getPackDescription());
		}
		if (reftableConfig != null) {
			for (DfsReftable table : reftablesBefore) {
				toPrune.add(table.getPackDescription());
			}
