		if (fanout == null || fanout.length == 0) {
			return;
		}
		pm.beginTask(JGitText.get().pruneLooseUnreferencedObjects
				fanout.length);
		try {
			for (String d : fanout) {
				pm.update(1);
				if (d.length() != 2)
					continue;
				File[] entries = new File(objects
				if (entries == null)
					continue;
				for (File f : entries) {
					String fName = f.getName();
					if (fName.length() != Constants.OBJECT_ID_STRING_LENGTH - 2)
