			} else {
				left.add(a);
			}
			pm.update(1);
		}

		for (List<DiffEntry> adds : nonUniqueAdds) {
			Object o = deletedMap.get(adds.get(0).newId);
			if (o instanceof DiffEntry) {
				DiffEntry d = (DiffEntry) o;
				DiffEntry best = bestPathMatch(d
