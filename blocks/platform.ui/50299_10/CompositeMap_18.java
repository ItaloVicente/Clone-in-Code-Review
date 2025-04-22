			for (Iterator<I> it = diff.getChangedKeys().iterator(); it
					.hasNext();) {
				I changedKey = it.next();
				Set<K> elements = firstMap.getKeys(changedKey);
				for (Iterator<K> it2 = elements.iterator(); it2.hasNext();) {
					K element = it2.next();
