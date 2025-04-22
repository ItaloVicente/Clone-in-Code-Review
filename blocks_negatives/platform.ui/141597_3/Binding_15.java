			hashCode = HASH_INITIAL;
			hashCode = hashCode * HASH_FACTOR
					+ Util.hashCode(getParameterizedCommand());
			hashCode = hashCode * HASH_FACTOR + Util.hashCode(getContextId());
			hashCode = hashCode * HASH_FACTOR
					+ Util.hashCode(getTriggerSequence());
			hashCode = hashCode * HASH_FACTOR + Util.hashCode(getLocale());
			hashCode = hashCode * HASH_FACTOR + Util.hashCode(getPlatform());
			hashCode = hashCode * HASH_FACTOR + Util.hashCode(getSchemeId());
			hashCode = hashCode * HASH_FACTOR + Util.hashCode(getType());
