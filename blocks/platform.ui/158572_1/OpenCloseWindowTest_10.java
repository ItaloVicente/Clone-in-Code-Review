		exercise(() -> {
			processEvents();
			EditorTestHelper.calmDown(500, 30000, 500);

			startMeasuring();
			IWorkbenchWindow window = openTestWindow(id);
			processEvents();
			window.close();
			processEvents();
			stopMeasuring();
