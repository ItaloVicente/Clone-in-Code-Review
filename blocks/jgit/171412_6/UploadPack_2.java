			Collection<Ref> references = null;
			if (req.getClientCapabilities().contains(OPTION_INCLUDE_TAG)) {
				Temporal startGetRefs = Instant.now();

				references = db.getRefDatabase().getRefsByPrefix(R_TAGS);

				long timeGetRefs = Duration.between(startGetRefs
						.toMillis();
				performanceLogContext.addEvent(
						new PerformanceLogRecord("get-refs"
			}
			sendPack(accumulator
					pckOut);
