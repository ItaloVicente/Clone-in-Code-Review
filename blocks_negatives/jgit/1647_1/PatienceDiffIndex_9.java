			if (lcs.getLengthB() < be - bs) {
				lcs.beginA = as;
				lcs.beginB = bs;
				lcs.endA = ae;
				lcs.endB = be;
				cIdx = nCnt;
			}

			nCommon[nCnt] = (((long) bs) << B_SHIFT) | (((long) be) << A_SHIFT);
			if (++nCnt == uniqueCommonCnt)
				break MATCH;
