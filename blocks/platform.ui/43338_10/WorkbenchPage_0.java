		Collections.sort(partIds, new Comparator<Object>() {
			@Override
			public int compare(Object ob1, Object ob2) {
				int index1 = mruShowInPartIds.indexOf(ob1);
				int index2 = mruShowInPartIds.indexOf(ob2);
				if (index1 != -1 && index2 == -1)
					return -1;
				if (index1 == -1 && index2 != -1)
					return 1;
				return index1 - index2;
			}
		});
