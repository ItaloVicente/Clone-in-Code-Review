		        desc.resourcesSorted = true;
		    }
		    folderNames.removeAll();
		    for (int i = 0; i < desc.resources.size(); i++) {
		        TableItem newItem = new TableItem(folderNames, SWT.NONE);
		        IResource r = (IResource) desc.resources.get(i);
		        newItem.setText(getParentLabel(r));
		        newItem.setImage(getParentImage(r));
		        newItem.setData(r);
		    }
		    folderNames.setSelection(0);
