			Map<String
			for (Ref r : lastPackedRefs) {
				last.put(r.getName()
			}
			newRefs = new ArrayList<>();
			for (Ref r : getAllRefs()) {
				Ref old = last.get(r.getName());
				if (!equals(r
					newRefs.add(r);
				}
