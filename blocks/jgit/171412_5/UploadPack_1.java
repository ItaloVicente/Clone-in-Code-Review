			Temporal startGetRefs = Instant.now();

			Collection<Ref> references = db.getRefDatabase().getRefs();

			long timeGetRefs = Duration.between(startGetRefs
					.toMillis();
			performanceLogContext.addEvent(
					new PerformanceLogRecord("get-refs"
