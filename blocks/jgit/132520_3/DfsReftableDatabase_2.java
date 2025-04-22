	@Override
	public boolean hasRefs(Map<String
		try {
			Reftable table = reader();
			for (Map.Entry<String
					.entrySet()) {
				RefCursor refCursor = table.seekRef(expectation.getKey());
				if (!refCursor.next()) {
					return false;
				}

				if (refCursor.getUpdateIndex() < expectation.getValue()
						.longValue()) {
					return false;
				}
			}
			return true;
		} catch (IOException e) {
			return false;
		}
	}


