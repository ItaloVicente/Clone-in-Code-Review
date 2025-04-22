			Node leafNode = new Node(index, getRandomChildCount(), this);
			getChildren()[index] = leafNode;
			fViewer.update(leafNode.getParent(), null);
			if (leafNode.getParent().getParent() == null) {
				fParentsLoaded++;
			} else {
				fGlobalChildrenLoaded++;
			}
			fChildrenLoaded++;
			return leafNode;
