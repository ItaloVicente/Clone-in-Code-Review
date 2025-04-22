
		if (reftableConfig != null) {
			for (DfsReftable table : objdb.getReftables()) {
				DfsPackDescription d = table.getPackDescription();
				if (d.getPackSource() != GC
						&& d.getFileSize(REFTABLE) < autoAddSize) {
					add(table);
				}
			}
		}
