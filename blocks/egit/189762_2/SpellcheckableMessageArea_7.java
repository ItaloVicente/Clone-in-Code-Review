		if (useCommentHighlight) {
			IDocumentPartitioner partitioner = new FastPartitioner(
					new CommitPartitionTokenScanner(() -> commentChar,
							() -> cleanupMode),
					configuration.getConfiguredContentTypes(sourceViewer));
			partitioner.connect(document);
			document.setDocumentPartitioner(partitioner);
		}
