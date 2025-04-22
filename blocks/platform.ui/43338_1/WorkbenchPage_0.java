		Collections.sort(partIds, new Comparator<Object>() {

			@Override
			public int compare(Object ob1, Object ob2) {
				if (mruPartIds.contains(ob1) && !mruPartIds.contains(ob2))
					return -1;
				if (!mruPartIds.contains(ob1) && mruPartIds.contains(ob2))
					return 1;
				return mruPartIds.indexOf(ob1) - mruPartIds.indexOf(ob2);
			}
		});
