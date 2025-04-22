					for (int v = 0; v < collectors.length; v++) {
						IProgressUpdateCollector collector = collectors[v];

						if (updateItems.length > 0) {
							collector.refresh(updateItems);
						}
						if (additionItems.length > 0) {
							collector.add(additionItems);
						}
						if (deletionItems.length > 0) {
							collector.remove(deletionItems);
						}
					}
