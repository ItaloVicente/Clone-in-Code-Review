	        Display display = fWindow.getShell().getDisplay();

	        while (!(uiJobFinished && backgroundThreadFinished) ) {
	            if (finalTime - System.currentTimeMillis() > 3000) {
	                break;
	            }
	            if (!display.readAndDispatch()) {
	                display.sleep();
	            }
	        }

	        Assert.assertTrue("Background thread did not finish (possible deadlock)", backgroundThreadFinished);
	        Assert.assertTrue("Test job did not finish (possible deadlock)", uiJobFinished);
	        Assert.assertFalse("Background thread was interrupted ", backgroundThreadInterrupted);
	        Assert.assertFalse("Background thread finished before the UIJob, even though the background thread was supposed to be waiting for the UIJob",
	                backgroundThreadFinishedBeforeUIJob);

	        Assert.assertFalse("Background thread finished before the UIJob, even though the background thread was supposed to be waiting for the UIJob",
	                backgroundThreadFinishedBeforeUIJob);

	        Assert.assertTrue("Background thread finished before the UIJob, even though the background thread was supposed to be waiting for the UIJob",
	                uiJobFinishedBeforeBackgroundThread);
        } finally {

        }

    }
