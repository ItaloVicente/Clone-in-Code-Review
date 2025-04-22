			switch (d.getPackSource()) {
			case GC:
				if (d.getFileSize(PackExt.PACK) < coalesceLiveLimit) {
					out.add(p);
				} else {
					newPackObj.add(objectIdSet(p.getPackIndex(ctx)));
				}
				break;
			case UNREACHABLE_GARBAGE:
				if (d.getFileSize(PackExt.PACK) < coalesceGarbageLimit) {
					out.add(p);
				}
				break;
			default:
