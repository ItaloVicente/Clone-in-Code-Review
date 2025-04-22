		if (isNoOp()) {
			return null;
		} else if (blobLimit == 0 && treeDepthLimit == -1) {
		} else if (blobLimit > 0 && treeDepthLimit == -1) {
		} else if (blobLimit == -1 && treeDepthLimit >= 0) {
		} else {
			throw new IllegalStateException();
