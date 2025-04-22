			if (ssel.getFirstElement() instanceof IResource) {
				showResource((IResource) ssel.getFirstElement());
				return;
			}
			IResource adapted = AdapterUtils.adapt(ssel.getFirstElement(),
					IResource.class);
