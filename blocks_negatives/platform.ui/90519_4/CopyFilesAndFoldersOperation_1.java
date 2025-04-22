			for (int i = 0; i < sourceStores.length; i++) {
				IFileStore sourceStore = sourceStores[i];
				IFileStore sourceParentStore = sourceStore.getParent();

				if (sourceStore != null) {
					if (destinationStore.equals(sourceStore)
							|| (sourceParentStore != null && destinationStore
							.equals(sourceParentStore))) {
