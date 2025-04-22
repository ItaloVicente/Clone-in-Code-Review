		if (containsSymrefs(pending)) {
			reject(pending.get(0)
					JGitText.get().atomicSymRefNotSupported
					pending);
			return;
		}
