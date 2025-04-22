			processEvents();
			waitForJobs(500, 3000);
			assertTrue(contextService.getActiveContextIds().contains(CONTEXT_ID1));

			int count = 0;
			while (count < 5 && listener1.count != 2) {
				count++;
				waitForJobs(100 * count, 1000);
			}

