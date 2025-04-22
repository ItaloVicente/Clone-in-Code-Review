		RefEntry entry = new RefEntry(ref
		if (lastRef != null && Entry.compare(lastRef
			throwIllegalEntry(lastRef
		}
		lastRef = entry;

		long blockPos = refs.write(entry);
