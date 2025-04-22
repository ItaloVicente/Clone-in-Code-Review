		if (!Util.equals(activityIds, castedObject.activityIds)) {
			return false;
		}

		if (!Util.equals(enabled, castedObject.enabled)) {
			return false;
		}

		return Util.equals(id, castedObject.id);
