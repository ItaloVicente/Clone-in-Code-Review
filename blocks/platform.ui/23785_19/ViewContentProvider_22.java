		}
		return category;
	}

	private boolean isView(MPartDescriptor descriptor) {
		List<String> tags = descriptor.getTags();
		for (String tag : tags) {
			if (tag.equals("View")) //$NON-NLS-1$
				return true;
		}
		return false;
	}
