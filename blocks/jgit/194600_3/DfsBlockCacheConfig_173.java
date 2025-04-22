
	public interface IndexEventConsumer {
		void acceptRequestedEvent(int packExtPos
				long loadMicros

		default void acceptEvictedEvent(int packExtPos
				int totalCacheHitCount
		}

		default boolean shouldReportEvictedEvent() {
			return false;
		}
	}
}
