			Collections.sort(want, new Comparator<ObjectIdWithOffset>() {
				public int compare(ObjectIdWithOffset a, ObjectIdWithOffset b) {
					return Long.signum(a.offset - b.offset);
				}
			});

