			byte[] subId = idSubmodule(entries[ptr]);
			if (subId != zeroid) {
				contentIdFromPtr = ptr;
				contentId = subId;
			}
			return subId;
