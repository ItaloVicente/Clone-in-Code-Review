		if (!Util.equals(requiredActivityId, castedObject.requiredActivityId)) {
			return false;
		}

		if (!Util.equals(activityId, castedObject.activityId)) {
			return false;
		}

		return Util.equals(sourceId, castedObject.sourceId);
