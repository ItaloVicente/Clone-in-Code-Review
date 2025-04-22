				if (nParents == 0) {
					pList[nParents++] = p;
				} else if (nParents == 1) {
					pList = new RevCommit[] { pList[0], p };
					nParents = 2;
				} else {
					if (pList.length <= nParents) {
						RevCommit[] old = pList;
						pList = new RevCommit[pList.length + 32];
						System.arraycopy(old, 0, pList, 0, nParents);
					}
					pList[nParents++] = p;
				}
