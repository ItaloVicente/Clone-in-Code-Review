		pack(refs
	}

	PackedRefList pack(Map<String
		return pack(heldLocks.keySet()
	}

	private PackedRefList pack(Collection<String> refs
			Map<String
		for (LockFile ol : heldLocks.values()) {
			ol.requireLock();
		}
		if (refs.size() == 0) {
			return null;
		}
