		if (commonAncestor != null) {
			ITypedElement ancestorCandidate = CompareUtils
					.getFileRevisionTypedElement(gitPath, commonAncestor, db);
			if (!ITypedElement.UNKNOWN_TYPE.equals(ancestorCandidate.getType()))
				ancestor = ancestorCandidate;
		}
