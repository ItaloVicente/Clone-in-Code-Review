		if (!Util.equals(activityPatternBindings, this.activityPatternBindings)) {
			this.activityPatternBindings = activityPatternBindings;
			this.activityPatternBindingsAsArray = this.activityPatternBindings
					.toArray(new IActivityPatternBinding[this.activityPatternBindings.size()]);
			hashCode = HASH_INITIAL;
			string = null;
			return true;
		}
