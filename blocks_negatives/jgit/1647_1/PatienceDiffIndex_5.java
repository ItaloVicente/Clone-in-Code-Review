				final long rec = ptrs[eIdx];
				final int bs = bOf(rec);

				if (isDuplicate(rec) || !cmp.equals(a, ptr, b, bs))
					continue;

				final int aPtr = aOfRaw(rec);
				if (aPtr != 0 && cmp.equals(a, ptr, a, aPtr - 1)) {
					ptrs[eIdx] = rec | A_DUPLICATE;
					uniqueCommonCnt--;
					ptr++;
