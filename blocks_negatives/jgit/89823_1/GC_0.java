		if (fanout != null && fanout.length > 0) {
			pm.beginTask(JGitText.get().pruneLooseUnreferencedObjects,
					fanout.length);
			try {
				for (String d : fanout) {
					pm.update(1);
					if (d.length() != 2)
