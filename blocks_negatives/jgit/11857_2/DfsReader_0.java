	private static final Comparator<ObjectToPack> WRITE_SORT = new Comparator<ObjectToPack>() {
		public int compare(ObjectToPack o1, ObjectToPack o2) {
			DfsObjectToPack a = (DfsObjectToPack) o1;
			DfsObjectToPack b = (DfsObjectToPack) o2;
			int cmp = a.packIndex - b.packIndex;
			if (cmp == 0)
				cmp = Long.signum(a.offset - b.offset);
			return cmp;
		}
	};

