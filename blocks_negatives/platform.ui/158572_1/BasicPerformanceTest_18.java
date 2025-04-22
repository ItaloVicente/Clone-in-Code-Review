		if (interactive) {
			NumberFormat f = new DecimalFormat("##.000");
			NumberFormat p = new DecimalFormat("#0.0");
			try {
				runnable.run();
				int initialRuns = 3;
				long startTime = System.currentTimeMillis();
				for (int i=0; i<initialRuns; i++) {
					runnable.run();
				}
				long currentTime = System.currentTimeMillis();
				double timePerRun = (currentTime - startTime) / 1000.0 / initialRuns;
				int totalRuns = initialRuns;
				long intervalMillis = (long) (1000 * interval);
				double averagePerInterval = interval/timePerRun;
				System.out.println("Time per run (roughly): " + f.format(timePerRun) + " - expecting " + f.format(averagePerInterval) + " runs per 10 seconds.");
				System.err.println("Remember - higher means faster: the following shows number of runs per interval (seconds=" + p.format(interval) + ").");
				while (true) {
					int numOperations = 0;
					long elapsed = 0;
					while (elapsed < intervalMillis) {
						startMeasuringTime = -1;
						stopMeasuringTime = -1;
						startTime = System.currentTimeMillis();
						numOperations++;
						runnable.run();
						currentTime = System.currentTimeMillis();
						if (startMeasuringTime != -1 && stopMeasuringTime != -1) {
							elapsed += stopMeasuringTime - startMeasuringTime;
						} else {
							elapsed += currentTime - startTime;
						}
					}
					timePerRun = elapsed / 1000.0 / numOperations;
					double operationsPerInterval = interval/timePerRun;
					double deviation = (operationsPerInterval - averagePerInterval) / averagePerInterval * 100.0;
					System.out.println(f.format(operationsPerInterval) + " runs/interval    (" + (deviation>=0.0?"+":"-") + p.format(Math.abs(deviation)) + "% relative to avg=" + f.format(averagePerInterval) + ")");
					averagePerInterval = ((averagePerInterval * totalRuns) + (operationsPerInterval * numOperations)) / (totalRuns + numOperations);
					totalRuns += numOperations;
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}
			return;
		}
