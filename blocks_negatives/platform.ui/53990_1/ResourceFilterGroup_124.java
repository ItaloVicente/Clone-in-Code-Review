		Arrays.sort(descriptors, new Comparator() {
			@Override
			public int compare(Object arg0, Object arg1) {
				if (((IFilterMatcherDescriptor) arg0).getId().equals(FileInfoAttributesMatcher.ID))
					return -1;
				if (((IFilterMatcherDescriptor) arg1).getId().equals(FileInfoAttributesMatcher.ID))
					return 1;
				return ((IFilterMatcherDescriptor) arg0).getId().compareTo(((IFilterMatcherDescriptor) arg1).getId());
			}
