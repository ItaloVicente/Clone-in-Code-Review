                final String [] ids = new String[count];
                for (int i = 0; i < ids.length; i++) {
                    long timestamp = System.currentTimeMillis();
                    ids[i] = "org.eclipse.jdt.ui/" + i + timestamp;
                }

                startMeasuring();
                for(int i = 0; i < ids.length; i++) {
                    activityManager.getIdentifier(ids[i]);
                }
                stopMeasuring();
            }
        });
        commitMeasurements();
        assertPerformance();
    }
