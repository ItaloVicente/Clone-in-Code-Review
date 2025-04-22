			try {
				PerformanceLogContext.getInstance().addEvent(record);
				eventRecordsSize = PerformanceLogContext.getInstance()
						.getEventRecords().size();
				PerformanceLogContext.getInstance().cleanEvents();
			} catch (Exception e) {
				thrown = true;
			}
