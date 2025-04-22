			for (IFileStore fileStore : sourceStores) {
				IFileStore parentFileStore = fileStore.getParent();

				if (fileStore != null) {
					if (destinationStore.equals(fileStore)
							|| (parentFileStore != null && destinationStore
							.equals(parentFileStore))) {
