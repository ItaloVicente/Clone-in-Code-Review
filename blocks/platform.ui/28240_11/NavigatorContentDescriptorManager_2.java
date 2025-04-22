		private void cleanUpStaleEntries() {

			Reference r;
			while ((r = evaluationsQueue.poll()) != null) {
				processStaleEntry(r, evaluations);
			}
			while ((r = evaluationsWithOverridesQueue.poll()) != null) {
				processStaleEntry(r, evaluationsWithOverrides);
			}
		}

		private static void processStaleEntry(Reference r, Map fromMap) {
			if (r instanceof EvaluationReference) {
				EvaluationValueReference oldVal = (EvaluationValueReference) fromMap.remove(r);
				if (oldVal != null) {
					oldVal.clear();
				}
			}
			if (r instanceof EvaluationValueReference) {
				EvaluationReference key = ((EvaluationValueReference)r).getKey();
				if (key != null) {
					fromMap.remove(key);
				}
			}
		}

