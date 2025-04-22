		if (reverseRefMap == null) {
			reverseRefMap = repository.getAllRefsByPeeledObjectId();
			for (Map.Entry<AnyObjectId
					.entrySet()) {
				Set<Ref> set = reverseRefMap.get(entry.getKey());
				Set<Ref> additional = entry.getValue();
				if (set != null) {
					if (additional.size() == 1) {
						additional = new HashSet<>(additional);
					}
					additional.addAll(set);
				}
				reverseRefMap.put(entry.getKey()
			}
			additionalRefMap.clear();
			additionalRefMap = null;
		}
