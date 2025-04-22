        BusyIndicator.showWhile(getShell().getDisplay(), new Runnable() {
            @Override
			public void run() {
            	Object[] items = null;
            	if (workingSet == null) {

            		IWorkbenchPage page = IDEWorkbenchPlugin.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage();
            		if(page == null) {
            			return;
            		}
            		IWorkbenchPart part = page.getActivePart();
            		if(part == null) {
            			return;
            		}
            		ISelection selection = page.getSelection();
            		if(selection instanceof IStructuredSelection) {
            			items = ((IStructuredSelection)selection).toArray();
            		}

        		} else {
        			items = workingSet.getElements();
        		}
            	if(items == null) {
            		return;
            	}
                tree.setCheckedElements(items);
                for (int i = 0; i < items.length; i++) {
                	IAdaptable item = null;
                	if(!(items[i] instanceof IAdaptable)) {
                		continue;
                	}
                	item = (IAdaptable)items[i];
                    IContainer container = null;
                    IResource resource = null;

                    if (item instanceof IContainer) {
                        container = (IContainer) item;
                    } else {
                        container = item.getAdapter(IContainer.class);
                    }
                    if (container != null) {
                        setSubtreeChecked(container, true, true);
                    }
                    if (item instanceof IResource) {
                        resource = (IResource) item;
                    } else {
                        resource = item.getAdapter(IResource.class);
                    }
                    if (resource != null && resource.isAccessible() == false) {
                        IProject project = resource.getProject();
                        if (tree.getChecked(project) == false) {
							tree.setGrayChecked(project, true);
						}
                    } else {
                        updateParentState(resource);
                    }
                }
            }
        });
