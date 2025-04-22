
		if (srcRef != null)
			this.srcRef = srcRef;
		else if (srcId != null && !srcId.equals(ObjectId.zeroId()))
			this.srcRef = srcId.name();
		else
			this.srcRef = null;

		if (srcId != null)
			this.newObjectId = srcId;
		else
			this.newObjectId = ObjectId.zeroId();

