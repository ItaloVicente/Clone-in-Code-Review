			Node element = ((Node) parent).getChild(index);
			fViewer.replace(parent, index, element);
			updateChildCount(element, -1);
			fText.setText("1 root, " + fParentsLoaded + " nodes and " + fGlobalChildrenLoaded + " leafs in memory...");
		}
	}
