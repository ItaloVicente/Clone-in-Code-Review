			try {
				PerformanceLogContext.getInstance().addEvent(record);
				eventRecordsCount = PerformanceLogContext.getInstance()
						.getEventRecords().size();
				PerformanceLogContext.getInstance().cleanEvents();
			} catch (Exception e) {
				thrown = true;
			}
