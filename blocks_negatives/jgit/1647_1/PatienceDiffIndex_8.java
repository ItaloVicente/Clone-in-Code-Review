			int as = aOf(rec);
			if (pIdx < pEnd) {
				final long priorRec = pCommon[pIdx];
				if (bs == bOf(priorRec)) {
					int be = aOfRaw(priorRec);

					if (lcs.getLengthB() < be - bs) {
						as -= bOf(rec) - bs;
						lcs.beginA = as;
						lcs.beginB = bs;
						lcs.endA = as + (be - bs);
						lcs.endB = be;
						cIdx = nCnt;
					}

					nCommon[nCnt] = priorRec;
					if (++nCnt == uniqueCommonCnt)
						break MATCH;

					pIdx++;
					continue MATCH;
				}
