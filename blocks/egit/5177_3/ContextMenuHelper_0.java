		int failCount = 0;
		int maxFailCount = 4;
		long sleepTime = 250;
		while (failCount <= maxFailCount)
			try {
				clickContextMenuInternal(bot, texts);
				if (failCount > 0)
					System.out.println("Retrying clickContextMenu succeeded");
				break;
			} catch (WidgetNotFoundException e) {
				failCount++;
				if (failCount > maxFailCount) {
					System.out.println("clickContextMenu failed " + failCount
							+ " times");
					throw e;
				}
				System.out.println("clickContextMenu failed. Retrying in "
						+ sleepTime + " ms");
				try {
					Thread.sleep(sleepTime);
					sleepTime *= 2;
				} catch (InterruptedException e1) {
				}
			}
	}
