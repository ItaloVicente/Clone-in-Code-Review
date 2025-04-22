		int failCount = 0;
		long sleepTime = 250;
		while (failCount < 5)
			try {
				clickContextMenuInternal(bot, texts);
				break;
			} catch (WidgetNotFoundException e) {
				System.out.println("clickContextMenu failed. Retrying in " + sleepTime + " ms");
				failCount++;
				try {
					Thread.sleep(sleepTime);
					sleepTime *= 2;
				} catch (InterruptedException e1) {
				}
			}
	}
