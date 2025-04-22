			} catch (NumberFormatException e) {}
			if (blobLimit >= 0) {
				return FilterSpec.blobFilter(blobLimit);
			}
			long treeDepthLimit = -1;
			try {
				treeDepthLimit = Long
			} catch (NumberFormatException e) {}
			if (treeDepthLimit >= 0) {
				return FilterSpec.treeDepthFilter(treeDepthLimit);
