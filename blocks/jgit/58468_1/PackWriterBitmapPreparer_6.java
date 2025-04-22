
			for (RevCommit c : selectionHelper) {
				if (cardinality == index + 1)
					break;

				if (!bitmap.contains(c))
