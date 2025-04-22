					@Override
					public Color getForeground(Object element, int index) {
						if (index == 0)
							return null;
						Person person = (Person) element;
						return (person.getGender() == Person.MALE) ? male
								: female;
					}
