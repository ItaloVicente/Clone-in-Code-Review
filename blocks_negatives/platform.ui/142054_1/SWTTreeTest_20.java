                processEvents();
                startMeasuring();
                for (int j = 0; j < TreeAddTest.TEST_COUNT; j++) {
                    tree.getItem(j);
                    processEvents();
                }
                stopMeasuring();
            }
        });
