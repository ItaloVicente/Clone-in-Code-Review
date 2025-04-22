					Person person = (Person) element;
					return (person.getGender() == Person.MALE) ? male : female;
				}

				@Override
				public void dispose() {
					super.dispose();
					female.dispose();
				}
			}
			viewer.setLabelProvider(new ColorLabelProvider(attributes));

			viewer.setInput(observableList);
