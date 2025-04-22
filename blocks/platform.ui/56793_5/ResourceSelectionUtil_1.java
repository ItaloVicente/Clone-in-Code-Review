			if (resourceIsType(resource, resourceMask)) {
				result.add(resource);
			}
		}
		return new StructuredSelection(result);
	}
