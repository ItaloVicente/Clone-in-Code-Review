			UITestCase.processEvents();
			assertStatusAdapter(TestStatusHandler.getLastHandledStatusAdapter());
			assertEquals(TestStatusHandler.getLastHandledStyle(), StatusManager.SHOW);
		} finally {
			dialog.close();
		}
