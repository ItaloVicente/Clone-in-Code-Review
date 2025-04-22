        return new IShowInTarget() {
            @Override
			public boolean show(ShowInContext context) {
                ArrayList toSelect = new ArrayList();
                ISelection sel = context.getSelection();
                if (sel instanceof IStructuredSelection) {
                    IStructuredSelection ssel = (IStructuredSelection) sel;
                    for (Iterator i = ssel.iterator(); i.hasNext();) {
                        Object o = i.next();
                        if (o instanceof IResource) {
                            toSelect.add(o);
                        } else if (o instanceof IMarker) {
                            IResource r = ((IMarker) o).getResource();
                            if (r.getType() != IResource.ROOT) {
                                toSelect.add(r);
                            }
                        } else if (o instanceof IAdaptable) {
                            IAdaptable adaptable = (IAdaptable) o;
                            o = adaptable.getAdapter(IResource.class);
                            if (o instanceof IResource) {
                                toSelect.add(o);
                            } else {
                                o = adaptable.getAdapter(IMarker.class);
                                if (o instanceof IMarker) {
                                    IResource r = ((IMarker) o).getResource();
                                    if (r.getType() != IResource.ROOT) {
                                        toSelect.add(r);
                                    }
                                }
                            }
                        }
                    }
                }
                if (toSelect.isEmpty()) {
                    Object input = context.getInput();
                    if (input instanceof IAdaptable) {
                        IAdaptable adaptable = (IAdaptable) input;
                        Object o = adaptable.getAdapter(IResource.class);
                        if (o instanceof IResource) {
                            toSelect.add(o);
                        }
                    }
                }
                if (!toSelect.isEmpty()) {
                    selectReveal(new StructuredSelection(toSelect));
                    return true;
                }
                return false;
            }
        };
