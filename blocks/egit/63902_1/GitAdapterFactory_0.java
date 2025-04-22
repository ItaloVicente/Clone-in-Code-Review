		if (adapterType == Repository.class) {
			ResourceMapping m = AdapterUtils.adapt(adaptableObject,
					ResourceMapping.class);
			if (m != null) {
				return SelectionUtils.getRepository(new StructuredSelection(m));
			}
		}

