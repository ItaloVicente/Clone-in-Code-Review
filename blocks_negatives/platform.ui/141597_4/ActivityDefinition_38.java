		if (!Util.equals(id, castedObject.id)) {
			return false;
		}

		if (!Util.equals(name, castedObject.name)) {
			return false;
		}

		return Util.equals(sourceId, castedObject.sourceId);
