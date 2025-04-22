				if (ph.getRef() == null) {
					unresolved.add(ph);
				}
			}
			for (MPlaceholder ph : unresolved) {
				replacePlaceholder(ph);
