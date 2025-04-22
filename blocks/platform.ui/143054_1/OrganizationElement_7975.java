		if (adapter == IPropertySource.class) {
			return (T)this;
		}
		if (adapter == IWorkbenchAdapter.class) {
			return (T)this;
		}
		return null;
	}

	static ArrayList<PropertyDescriptor> getDescriptors() {
		return descriptors;
	}

	@Override
