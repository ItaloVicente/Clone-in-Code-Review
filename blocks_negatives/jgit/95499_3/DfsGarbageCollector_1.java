			if (packsBefore.isEmpty()) {
				if (!expiredGarbagePacks.isEmpty()) {
					objdb.commitPack(noPacks(), toPrune());
				}
				return true;
			}

