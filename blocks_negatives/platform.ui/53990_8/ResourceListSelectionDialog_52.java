        BusyIndicator.showWhile(getShell().getDisplay(), new Runnable() {
            @Override
			public void run() {
                if (!desc.resourcesSorted) {
                    Collections.sort(desc.resources, new Comparator() {
                        @Override
						public int compare(Object o1, Object o2) {
                            String s1 = getParentLabel((IResource) o1);
                            String s2 = getParentLabel((IResource) o2);
                            return collator.compare(s1, s2);
                        }
                    });
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
            }
        });
