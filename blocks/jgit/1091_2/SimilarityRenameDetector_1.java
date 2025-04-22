				long srcSize = srcSizes[srcIdx];
				if (srcSize < 0) {
					srcSize = size(srcEnt.oldId.toObjectId());
					srcSizes[srcIdx] = srcSize;
				}

				long dstSize = dstSizes[dstIdx];
				if (dstSize < 0) {
					dstSize = size(dstEnt.newId.toObjectId());
					dstSizes[dstIdx] = dstSize;
				}

				long max = Math.max(srcSize
				long min = Math.min(srcSize
				if (min * 100 / max < renameScore) {
					pm.update(1);
					continue;
				}

