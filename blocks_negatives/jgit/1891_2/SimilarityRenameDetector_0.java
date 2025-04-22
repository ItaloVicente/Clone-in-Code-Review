			SimilarityIndex s;
			try {
				s = hash(OLD, srcEnt);
			} catch (TableFullException tableFull) {
				tableOverflow = true;
				continue;
			}
