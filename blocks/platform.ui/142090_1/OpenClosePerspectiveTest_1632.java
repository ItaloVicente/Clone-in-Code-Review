				processEvents();
				EditorTestHelper.calmDown(500, 30000, 500);

				startMeasuring();
				activePage.setPerspective(perspective1);
				processEvents();
				closePerspective(activePage);
				processEvents();
				stopMeasuring();
			}
		});

		commitMeasurements();
		assertPerformance();
	}

	private void closePerspective(IWorkbenchPage activePage) {
