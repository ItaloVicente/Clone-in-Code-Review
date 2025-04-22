			PerformanceLogContext.getInstance()
					.addEvent(new PerformanceLogRecord("negotiation"
							accumulator.timeNegotiating));
			
			performanceLogHook.onEndOfCommand(
					PerformanceLogContext.getInstance().getEventRecords());

