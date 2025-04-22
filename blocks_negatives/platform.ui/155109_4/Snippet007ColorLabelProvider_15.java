				@Override
				public String getColumnText(Object element, int index) {
					if (index == 0) {
						return Integer.toString(persons.indexOf(element) + 1);
					}
					return ((Person) element).getName();
