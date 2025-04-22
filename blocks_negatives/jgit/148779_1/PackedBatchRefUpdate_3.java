			while (addIdx < adds.size()) {
				ReceiveCommand currAdd = adds.get(addIdx);
				if (currAdd.getRefName().compareTo(name) < 0) {
					b.add(peeledRef(walk, currAdd));
					byName.remove(currAdd.getRefName());
				} else {
					break;
