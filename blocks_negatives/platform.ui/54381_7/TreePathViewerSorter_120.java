        Arrays.sort(elements, new Comparator() {
            @Override
			public int compare(Object a, Object b) {
                return TreePathViewerSorter.this.compare(viewer, parentPath, a, b);
            }
        });
