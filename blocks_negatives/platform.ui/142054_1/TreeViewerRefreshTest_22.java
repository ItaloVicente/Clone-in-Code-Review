        for (int i = 0; i < ITERATIONS; i++) {
            startMeasuring();
            viewer.refresh();
            processEvents();
            stopMeasuring();
        }
