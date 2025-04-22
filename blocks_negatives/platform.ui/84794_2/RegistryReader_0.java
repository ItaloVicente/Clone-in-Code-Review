        IExtension[] sortedExtension = new IExtension[extensions.length];
        System.arraycopy(extensions, 0, sortedExtension, 0, extensions.length);
        Comparator comparer = new Comparator() {
            @Override
			public int compare(Object arg0, Object arg1) {
				String s1 = ((IExtension) arg0).getNamespaceIdentifier();
				String s2 = ((IExtension) arg1).getNamespaceIdentifier();
                return s1.compareToIgnoreCase(s2);
            }
        };
        Collections.sort(Arrays.asList(sortedExtension), comparer);
        return sortedExtension;
