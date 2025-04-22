		Arrays.sort(filterDescriptors, new Comparator<ICommonFilterDescriptor>() {

			@Override
			public int compare(ICommonFilterDescriptor o1, ICommonFilterDescriptor o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});
