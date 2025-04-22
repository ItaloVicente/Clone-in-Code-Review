			return seekRefPrefix(name);
		}
		return seekSingleRef(name);
	}

	private RefCursor seekRefPrefix(String name) throws IOException {
