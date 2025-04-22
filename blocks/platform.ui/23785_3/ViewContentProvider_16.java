			Set<Object> combinedTopElements = new HashSet<Object>();
			combinedTopElements.addAll(categoryTags);
			combinedTopElements.addAll(noCategoryDescriptors);
			return combinedTopElements.toArray();
		} else if (element instanceof String) {
			List<MPartDescriptor> descriptors = application.getDescriptors();
			Set<MPartDescriptor> categoryDescriptors = new HashSet<MPartDescriptor>();
			for (MPartDescriptor descriptor : descriptors) {
				List<String> tags = descriptor.getTags();
				for (String tag : tags) {
					if (!tag.startsWith(CATEGORY_TAG))
						continue;
					String categoryTag = tag.substring(CATEGORY_TAG_LENGTH);
					if (element.equals(categoryTag))
						categoryDescriptors.add(descriptor);
				}
			}
			return categoryDescriptors.toArray();
		}
		return new Object[0];
	}
