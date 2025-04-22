		private void cleanUpStaleEntries() {
			long totalCount = 0;
			long individualCount = 0;
			long individualCountOverrides = 0;
			Reference r;
			while ((r = evaluationsQueue.poll()) != null) {
				processStaleEntry(r, evaluations);
				++totalCount;
				++individualCount;
			}
			while ((r = evaluationsWithOverridesQueue.poll()) != null) {
				processStaleEntry(r, evaluationsWithOverrides);
				++totalCount;
				++individualCountOverrides;
			}
			setIfGreaterMaxRefQueueCleanedSum(totalCount);
			setIfGreaterMaxRefQueueCleanedIndividual(Math.max(individualCount, individualCountOverrides));
		}

		private void processStaleEntry(Reference r, Map fromMap) {
			if (r instanceof EvaluationReference) {
				EvaluationValueReference oldVal = (EvaluationValueReference) fromMap.remove(r);
				if (oldVal != null) {
					oldVal.clear();
					incrementRemoveCount();
					incrementRemoveReqQueueCount();
				}
			}
			if (r instanceof EvaluationValueReference) {
				EvaluationReference key = ((EvaluationValueReference)r).getKey();
				if (key != null) {
					if (fromMap.remove(key) != null) {
						incrementRemoveCount();
						incrementRemoveReqQueueCount();
					}
				}
			}
		}

