		if (!flagList.contains(f)) {
			if (!hasFlags) {
				hasFlags = true;
				extralength += 4;
				totalbody += 4;
			}
			if (f.equals(TapFlag.BACKFILL)) {
				hasBackfill = true;
				totalbody += 8;
			}if (f.equals(TapFlag.LIST_VBUCKETS)
					|| f.equals(TapFlag.TAKEOVER_VBUCKETS)) {
				hasVBucketList = true;
				totalbody += 2;
			}
			flagList.add(f);
