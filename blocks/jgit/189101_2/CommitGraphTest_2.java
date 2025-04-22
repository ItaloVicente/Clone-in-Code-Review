
	RevCommit commit(RevTree tree
		return tr.commit(tree
	}

	RevBlob blob(String content) throws Exception {
		return tr.blob(content);
	}

	DirCacheEntry file(String path
		return tr.file(path
	}

	RevTree tree(DirCacheEntry... entries) throws Exception {
		return tr.tree(entries);
	}
