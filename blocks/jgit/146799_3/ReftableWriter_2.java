		RefEntry entry = new RefEntry(ref
		if (lastRef != null && Entry.compare(lastRef
			illegalEntry(lastRef
		}
		lastRef = entry;

		long blockPos = refs.write(entry);
