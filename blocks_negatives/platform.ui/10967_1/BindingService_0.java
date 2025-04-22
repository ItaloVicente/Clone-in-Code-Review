		final TriggerSequence[] prefixes = trigger.getPrefixes();
		final int prefixesLength = prefixes.length;
		if (prefixesLength == 0) {
			return Collections.EMPTY_MAP;
		}

		Collection<Binding> partialMatches = bindingService.getPartialMatches(trigger);
		Map<TriggerSequence, Object> prefixTable = new HashMap<TriggerSequence, Object>();
		for (Binding binding : partialMatches) {
			for (int i = 0; i < prefixesLength; i++) {
				final TriggerSequence prefix = prefixes[i];
				final Object value = prefixTable.get(prefix);
				if ((prefixTable.containsKey(prefix)) && (value instanceof Map)) {
					((Map) value).put(prefixTable, binding);
				} else {
					final Map map = new HashMap();
					prefixTable.put(prefix, map);
					map.put(prefixTable, binding);
				}
			}
		}
		return prefixTable;
