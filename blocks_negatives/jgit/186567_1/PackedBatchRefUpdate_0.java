				Iterable<Entry<String, Ref>> loader = new Iterable<>() {

					private int i = 0;

					@Override
					public Iterator<Entry<String, Ref>> iterator() {
						Iterator<Entry<String, Ref>> it = new Iterator<>() {
