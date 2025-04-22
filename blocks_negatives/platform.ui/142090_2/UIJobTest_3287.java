            testJob.schedule();

            backgroundThreadStarted = true;

            try {
                testJob.join();
                uiJobFinishedBeforeBackgroundThread = uiJobFinished;
                backgroundThreadFinished = true;
            } catch (InterruptedException e) {
                backgroundThreadInterrupted = true;
            }
        }
        };

        Job delayJob = new Job("blah") {
            @Override
