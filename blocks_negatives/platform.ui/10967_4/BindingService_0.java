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
