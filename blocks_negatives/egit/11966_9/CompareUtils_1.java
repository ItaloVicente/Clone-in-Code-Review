

	/**
	 * Extracted from {@link CompareWithCommitActionHandler}
	 * @param actLeft
	 * @param actRight
	 * @return compare input
	 */
	public static DiffNode prepareGitCompare(ITypedElement actLeft, ITypedElement actRight) {
		if (actLeft.getType().equals(ITypedElement.FOLDER_TYPE)) {
			DiffNode diffNode = new DiffNode(null,Differencer.CHANGE,null,actLeft,actRight);
			ITypedElement[] lc = (ITypedElement[])((IStructureComparator)actLeft).getChildren();
			ITypedElement[] rc = (ITypedElement[])((IStructureComparator)actRight).getChildren();
			int li=0;
			int ri=0;
			while (li<lc.length && ri<rc.length) {
				ITypedElement ln = lc[li];
				ITypedElement rn = rc[ri];
				int compareTo = ln.getName().compareTo(rn.getName());
				if (compareTo == 0) {
					if (!ln.equals(rn))
						diffNode.add(prepareGitCompare(ln,rn));
					++li;
					++ri;
				} else if (compareTo < 0) {
					DiffNode childDiffNode = new DiffNode(Differencer.ADDITION, null, ln, null);
					diffNode.add(childDiffNode);
					if (ln.getType().equals(ITypedElement.FOLDER_TYPE)) {
						ITypedElement[] children = (ITypedElement[])((IStructureComparator)ln).getChildren();
						if(children != null && children.length > 0) {
							for (ITypedElement child : children) {
								childDiffNode.add(addDirectoryFiles(child, Differencer.ADDITION));
							}
						}
					}
					++li;
				} else {
					DiffNode childDiffNode = new DiffNode(Differencer.DELETION, null, null, rn);
					diffNode.add(childDiffNode);
					if (rn.getType().equals(ITypedElement.FOLDER_TYPE)) {
						ITypedElement[] children = (ITypedElement[])((IStructureComparator)rn).getChildren();
						if(children != null && children.length > 0) {
							for (ITypedElement child : children) {
								childDiffNode.add(addDirectoryFiles(child, Differencer.DELETION));
							}
						}
					}
					++ri;
				}
			}
			while (li<lc.length) {
				ITypedElement ln = lc[li];
				DiffNode childDiffNode = new DiffNode(Differencer.ADDITION, null, ln, null);
				diffNode.add(childDiffNode);
				if (ln.getType().equals(ITypedElement.FOLDER_TYPE)) {
					ITypedElement[] children = (ITypedElement[])((IStructureComparator)ln).getChildren();
					if(children != null && children.length > 0) {
						for (ITypedElement child : children) {
							childDiffNode.add(addDirectoryFiles(child, Differencer.ADDITION));
						}
					}
				}
				++li;
			}
			while (ri<rc.length) {
				ITypedElement rn = rc[ri];
				DiffNode childDiffNode = new DiffNode(Differencer.DELETION, null, null, rn);
				diffNode.add(childDiffNode);
				if (rn.getType().equals(ITypedElement.FOLDER_TYPE)) {
					ITypedElement[] children = (ITypedElement[])((IStructureComparator)rn).getChildren();
					if(children != null && children.length > 0) {
						for (ITypedElement child : children) {
							childDiffNode.add(addDirectoryFiles(child, Differencer.DELETION));
						}
					}
				}
				++ri;
			}
			return diffNode;
		} else {
			return new DiffNode(actLeft, actRight);
		}
	}

	/**
	 * Extracted from {@link CompareWithCommitActionHandler}
	 * @param elem
	 * @param diffType
	 * @return diffnode
	 */
	private static DiffNode addDirectoryFiles(ITypedElement elem, int diffType) {
		ITypedElement l = null;
		ITypedElement r = null;
		if (diffType == Differencer.DELETION) {
			r = elem;
		} else {
			l = elem;
		}

		if (elem.getType().equals(ITypedElement.FOLDER_TYPE)) {
			DiffNode diffNode = null;
			diffNode = new DiffNode(null,Differencer.CHANGE,null,l,r);
			ITypedElement[] children = (ITypedElement[])((IStructureComparator)elem).getChildren();
			for (ITypedElement child : children) {
				diffNode.add(addDirectoryFiles(child, diffType));
			}
			return diffNode;
		} else {
			return new DiffNode(diffType, null, l, r);
		}
	}

