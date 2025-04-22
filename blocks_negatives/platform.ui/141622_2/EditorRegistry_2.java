				for (int i = 0; i < related.length; i++) {
					if (!allRelated.contains(related[i])) {
						if (!WorkbenchActivityHelper.filterItem(related[i])) {
							allRelated.add(related[i]);
						}
					}
