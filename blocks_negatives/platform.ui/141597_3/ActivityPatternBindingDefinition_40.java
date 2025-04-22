		if (!Util.equals(activityId, castedObject.activityId)) {
			return false;
		}

		if (!Util.equals(pattern, castedObject.pattern)) {
			return false;
		}

		if (!Util.equals(isEqualityPattern, castedObject.isEqualityPattern)) {
			return false;
		}

		return Util.equals(sourceId, castedObject.sourceId);
