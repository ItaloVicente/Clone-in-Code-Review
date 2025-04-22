		if (commonAncestor != null) {
			ITypedElement ancestorCandidate = CompareUtils
					.getFileRevisionTypedElement(gitPath, commonAncestor, db);
			if (!(ancestorCandidate instanceof EmptyTypedElement))
				ancestor = ancestorCandidate;
		}
