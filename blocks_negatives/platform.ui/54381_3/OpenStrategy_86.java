                    runnable[0] = new Runnable() {
                        @Override
						public void run() {
                            long time = System.currentTimeMillis();
                            int diff = (int) (time - startTime);
                            if (diff <= TIME) {
                                display.timerExec(diff * 2 / 3, runnable[0]);
                            } else {
                                timerStarted = false;
                                setSelection(mouseMoveEvent);
                            }
                        }
                    };
