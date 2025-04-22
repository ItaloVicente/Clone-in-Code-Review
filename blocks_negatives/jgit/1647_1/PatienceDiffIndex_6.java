
				ptrs[eIdx] = rec | (((long) (ptr + 1)) << A_SHIFT);
				uniqueCommonCnt++;

				if (pBegin < pEnd) {
					for (int pIdx = pLast + 1;; pIdx++) {
						if (pIdx == pEnd)
							pIdx = pBegin;
						else if (pIdx == pLast)
							break;

						final long priorRec = pCommon[pIdx];
						final int priorB = bOf(priorRec);
						if (bs < priorB)
							break;
						if (bs == priorB) {
							ptr += aOfRaw(priorRec) - priorB;
							pLast = pIdx;
							continue SCAN;
						}
					}
				}

				ptr++;
				continue SCAN;
