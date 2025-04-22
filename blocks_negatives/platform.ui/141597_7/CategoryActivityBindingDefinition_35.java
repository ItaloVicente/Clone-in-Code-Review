		if (!Util.equals(activityId, castedObject.activityId)) {
			return false;
		}

		if (!Util.equals(categoryId, castedObject.categoryId)) {
			return false;
		}

		return Util.equals(sourceId, castedObject.sourceId);
