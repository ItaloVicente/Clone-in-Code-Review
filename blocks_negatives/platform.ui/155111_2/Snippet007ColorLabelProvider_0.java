		class ColorLabelProvider extends ObservableMapLabelProvider implements ITableColorProvider {
			Color male = shell.getDisplay().getSystemColor(SWT.COLOR_BLUE);

			Color female = new Color(shell.getDisplay(), 255, 192, 203);

			ColorLabelProvider(IObservableMap<?, ?>[] attributes) {
				super(attributes);
			}

			@Override
			public String getColumnText(Object element, int index) {
				if (index == 0) {
					return Integer.toString(persons.indexOf(element) + 1);
				}
				return ((Person) element).getName();
			}

			@Override
			public Color getBackground(Object element, int index) {
				return null;
			}

			@Override
			public Color getForeground(Object element, int index) {
				if (index == 0) {
					return null;
				}
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
