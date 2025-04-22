			if (config.isDeltaCompress()) {
				int threads = config.getThreads();
				if (threads <= 0)
					threads = Runtime.getRuntime().availableProcessors();
				totalDeltaSearchBytes = (threads * config.getDeltaSearchMemoryLimit())
						+ config.getBigFileThreshold();
			} else
				totalDeltaSearchBytes = 0;
