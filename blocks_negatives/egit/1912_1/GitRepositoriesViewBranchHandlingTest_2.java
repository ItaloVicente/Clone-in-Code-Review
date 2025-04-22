			for (int i = 0; i < 1000; i++) {
				if (done[0])
					break;
				Thread.sleep(10);
			}
			assertTrue("Job should be completed", done[0]);

