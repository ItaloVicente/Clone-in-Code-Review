		private void setDescriptorsInMap(Object anElement, NavigatorContentDescriptor[] theDescriptors, Map map, ReferenceQueue queue) {
			EvaluationReference key = new EvaluationReference(anElement, queue);
			EvaluationValueReference newValue = new EvaluationValueReference(theDescriptors, key, queue);
			EvaluationValueReference oldValue = (EvaluationValueReference) map.put(key, newValue);
			incrementSetCount();
			if (oldValue != null) {
				newValue.swapKey(oldValue);
				oldValue.clear();
				incrementReplaceCount();
			}
