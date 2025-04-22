		private void setIfGreaterMaxRefQueueCleanedSum(long newVal) {
			synchronized(statLock) {
				if (newVal > maxRefQueueCleanedSum)
					maxRefQueueCleanedSum = newVal;
			}
		}

		private void setIfGreaterMaxRefQueueCleanedIndividual(long newVal) {
			synchronized(statLock) {
				if (newVal > maxRefQueueCleanedIndividual)
					maxRefQueueCleanedIndividual = newVal;
			}
		}
