			Thread newThread = new Thread(() -> {

				try {
					Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
				}

				display.asyncExec(() -> {
					final Set<Object> toAdd = new HashSet<>();
					final Set<Object> toRemove = new HashSet<>();

					int delta = (randomNumberGenerator.nextInt(AVERAGE_DELTA * 4) - AVERAGE_DELTA * 2);
					int extraAdds = randomNumberGenerator.nextInt(AVERAGE_DELTA);
					int addCount = delta + extraAdds;
					int removeCount = -delta + extraAdds;

					if (addCount > 0) {
						for (int i1 = 0; i1 < addCount; i1++) {
							toAdd.add(Integer.valueOf(randomNumberGenerator.nextInt(20)));
						}
