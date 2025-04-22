
	private static void checkContainsRef(Collection<Ref> haystack
		for (Ref ref : haystack) {
			if (ref.getName().equals(needle.getName()) &&
					ref.getObjectId().equals(needle.getObjectId())) {
				return;
			}
		}
		fail("list " + haystack + " does not contain ref " + needle);
	}
