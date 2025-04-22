	private boolean packIsCoalesceableGarbage(DfsPackDescription d
		return d.getPackSource() == UNREACHABLE_GARBAGE
				&& d.getFileSize(PackExt.PACK) < coalesceGarbageLimit
				&& (garbageTtlMillis == 0
						|| now - d.getLastModified() < garbageTtlMillis / 3);
	}

