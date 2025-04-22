				ITypedElement anc;
				if (ancestorCommit != null)
					anc = CompareUtils.getFileRevisionTypedElement(gitPath,
							ancestorCommit, repository);
				else
					anc = null;
				if (anc instanceof EmptyTypedElement)
					anc = null;
				new DiffNode(fileParent, kind, anc, leftEditable, right);
