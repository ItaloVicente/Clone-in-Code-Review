	public void send(final Map<String, Ref> refs) throws IOException {
		for (final Ref r : getSortedRefs(refs)) {
			final RevObject obj = parseAnyOrNull(r.getObjectId());
			if (obj != null) {
				advertiseAny(obj, r.getName());
				if (derefTags && obj instanceof RevTag)
					advertiseTag((RevTag) obj, r.getName() + "^{}");
