        	Object[] elements = treeContentProvider.getElements(root);
        	if(elements.length > 0) {
        		StructuredSelection selection = new StructuredSelection(elements[0]);
        		treeViewer.setSelection(selection);
        	}
        }

    }
