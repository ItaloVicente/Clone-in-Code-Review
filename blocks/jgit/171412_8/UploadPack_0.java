			Temporal startGetRefs = Instant.now();

			Map<String

			long timeGetRefs = Duration.between(startGetRefs
					.toMillis();
			performanceLogContext.addEvent(
					new PerformanceLogRecord(EVENT_GET_REFS

			advertised = refIdSet(references.values());
