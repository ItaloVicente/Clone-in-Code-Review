
			int length = 0;
			if (element instanceof IntermediateNode) {
				IntermediateNode node = (IntermediateNode) element;
				length =  node.children.length;
			}
			if(element == elements)
				length = elements.length;
			viewer.setChildCount(element, length);


