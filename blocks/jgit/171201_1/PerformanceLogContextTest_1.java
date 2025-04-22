			try {
				PerformanceLogContext.getInstance().addEvent(record);
				assertEquals(1
						.getEventRecords().size());
				PerformanceLogContext.getInstance().cleanEvents();
			} catch (Exception e) {
				thrown = true;
			}
