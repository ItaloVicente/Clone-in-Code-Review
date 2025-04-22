			while (!untracked.isEmpty() && untracked.getLast().startsWith(toBeAdded))
				untracked.removeLast();
			untracked.addLast(toBeAdded);
		}
		LinkedList<String> ret = new LinkedList<String>();
		for (String p: untracked) {
			boolean ignored = false;
			for (String i: ignoredPaths) {
				if (p.startsWith(i)) {
					ignored = true;
					break;
				}
			}
			if (!ignored)
				ret.add(p);
