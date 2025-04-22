		private NavigatorContentDescriptor[] getDescriptorsFromMap (Object anElement, Map map) {
			EvaluationReference key = new EvaluationReference(anElement);
			NavigatorContentDescriptor[] cachedDescriptors = null;
			Reference cache = (Reference) map.get(key);
			if (cache != null && (cachedDescriptors = (NavigatorContentDescriptor[]) cache.get()) == null) {
				EvaluationValueReference value = (EvaluationValueReference) map.remove(key);
				if (value != null) {
					value.clear();
					incrementRemoveCount();
				}
			}
			if (cachedDescriptors == null) {
				incrementMissCount();
			} else {
				incrementHitCount();
			}
			return cachedDescriptors;
		}

