		Collections.sort(all, new Comparator<Test>() {
			@Override
			public int compare(Test a, Test b) {
				int result = Long.signum(a.runningTimeNanos - b.runningTimeNanos);
				if (result == 0) {
					result = a.algorithm.name.compareTo(b.algorithm.name);
				}
				return result;
			}
		});
