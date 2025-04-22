		            if (o1 instanceof IResource) {
		                toSelect.add(o1);
		            } else if (o1 instanceof IMarker) {
		                IResource r1 = ((IMarker) o1).getResource();
		                if (r1.getType() != IResource.ROOT) {
		                    toSelect.add(r1);
		                }
		            } else if (o1 instanceof IAdaptable) {
		                IAdaptable adaptable1 = (IAdaptable) o1;
		                o1 = adaptable1.getAdapter(IResource.class);
		                if (o1 instanceof IResource) {
		                    toSelect.add(o1);
		                } else {
		                    o1 = adaptable1.getAdapter(IMarker.class);
		                    if (o1 instanceof IMarker) {
		                        IResource r2 = ((IMarker) o1).getResource();
		                        if (r2.getType() != IResource.ROOT) {
		                            toSelect.add(r2);
		                        }
		                    }
		                }
		            }
		        }
		    }
		    if (toSelect.isEmpty()) {
		        Object input = context.getInput();
		        if (input instanceof IAdaptable) {
		            IAdaptable adaptable2 = (IAdaptable) input;
		            Object o2 = adaptable2.getAdapter(IResource.class);
		            if (o2 instanceof IResource) {
		                toSelect.add(o2);
