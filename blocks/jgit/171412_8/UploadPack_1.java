			Collection<Ref> tags = null;
			if (req.getClientCapabilities().contains(OPTION_INCLUDE_TAG)) {
				Temporal startGetRefs = Instant.now();

				tags = db.getRefDatabase().getRefsByPrefix(R_TAGS);

				long timeGetRefs = Duration.between(startGetRefs
						.toMillis();
				performanceLogContext.addEvent(
						new PerformanceLogRecord(EVENT_GET_REFS
			}
			sendPack(accumulator
					pckOut);
