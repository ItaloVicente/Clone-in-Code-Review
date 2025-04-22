	public static class EvaluationCache implements VisibilityListener {

		private final Map evaluations/* <EvaluationReference<Object>, EvaluationValueReference<NavigatorContentDescriptor[]>> */ = new HashMap();
		private final Map evaluationsWithOverrides/* <EvaluationReference<Object>, EvaluationValueReference<NavigatorContentDescriptor[]>>*/ = new HashMap();

		private final ReferenceQueue evaluationsQueue = new ReferenceQueue();
		private final ReferenceQueue evaluationsWithOverridesQueue = new ReferenceQueue();

		private final Object statLock = new Object();

		long hitCount = 0;
		long setCount = 0;
		long replaceCount = 0;
		long missCount = 0;
		long removeCount = 0;
		long removeRefQueueCount = 0;
		long clearCount = 0;
		long maxRefQueueCleanedSum = 0;
		long maxRefQueueCleanedIndividual = 0;

		private void incrementHitCount() {
			synchronized(statLock) {
				++hitCount;
			}
		}

		private void incrementSetCount() {
			synchronized(statLock) {
				++setCount;
			}
		}

		private void incrementReplaceCount() {
			synchronized(statLock) {
				++replaceCount;
			}
		}

		private void incrementMissCount() {
			synchronized(statLock) {
				++missCount;
			}
		}

		private void incrementRemoveCount() {
			synchronized(statLock) {
				++removeCount;
			}
		}

		private void incrementRemoveReqQueueCount() {
			synchronized(statLock) {
				++removeRefQueueCount;
			}
		}


		private void incrementClearCount() {
			synchronized(statLock) {
				++clearCount;
			}
		}
