		contactsViewer.setComparator(new ViewerComparator() {
			@Override
			public int compare(final Viewer viewer, final Object obj1,
					final Object obj2) {

				if (obj1 instanceof Contact && obj2 instanceof Contact) {
					String lastName1 = ((Contact) obj1).getLastName();
					String lastName2 = ((Contact) obj2).getLastName();
					return lastName1.compareTo(lastName2);
				}
				throw new IllegalArgumentException(
						"Can only compare two Contacts.");

			}
		});
