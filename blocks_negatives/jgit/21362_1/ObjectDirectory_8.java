			if (matches.size() == oldSize) {
				PackList nList = scanPacks(pList);
				if (nList == pList || nList.packs.length == 0)
					break;
				pList = nList;
				continue;
			}
			break;
		}
