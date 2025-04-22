		if (containsSymrefs(pending)) {
			reject(pending.get(0)
					"atomic symref unsupported"
					pending);
			return;
		}
