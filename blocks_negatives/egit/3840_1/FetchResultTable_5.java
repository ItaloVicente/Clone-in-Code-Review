				if (r == RefUpdate.Result.FORCED) {
					final String o = safeAbbreviate(tru.getOldObjectId());
					final String n = safeAbbreviate(tru.getNewObjectId());
				}

				if (r == RefUpdate.Result.FAST_FORWARD) {
					final String o = safeAbbreviate(tru.getOldObjectId());
					final String n = safeAbbreviate(tru.getNewObjectId());
				}
