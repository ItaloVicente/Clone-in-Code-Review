		if (!(o1 instanceof IResource && o2 instanceof IResource)) {
			return compareClass(o1, o2);
		}
		IResource r1 = (IResource) o1;
		IResource r2 = (IResource) o2;

		if (r1 instanceof IContainer && r2 instanceof IContainer) {
