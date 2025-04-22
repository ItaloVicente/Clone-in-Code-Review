
		if (referenceMap == modelRefCounts) {
			if (result) {
				rememberRefKey(key);
			} else {
				incrementRefKeys(key);
			}
		}

