				DiffNode node = new MergeDiffNode(fileParent, kind, ancestor,
						left, right);
				if (stage2FromWorkingTree) {
					customLabels.put(node, tw.getNameString());
				} else if (left instanceof EditableRevision) {
					String name = tw.getNameString();
					((EditableRevision) left).addContentChangeListener(
							source -> setLeftLabel(node, name, true));
				}
