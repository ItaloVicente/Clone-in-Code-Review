
		void markHotter() {
			int cap = DfsBlockCache.getInstance().cacheHitCaps[key.packExtPos];
			hotCount = Math.min(cap
		}

		void markColder() {
			hotCount = Math.max(0
		}

		boolean isHot() {
			return hotCount > 0;
		}
