		for (WorkingSetDescriptor descriptor : all) {
			if (descriptor.isEditable()) {
				editable.add(descriptor);
			}
		}
		return editable.toArray(new WorkingSetDescriptor[editable.size()]);
	}
