			if (toSelect.isEmpty()) {
				Object input = context.getInput();
				IResource resource = Adapters.getAdapter(input, IResource.class, true);
				if (resource != null) {
					toSelect.add(resource);
				}
			}
