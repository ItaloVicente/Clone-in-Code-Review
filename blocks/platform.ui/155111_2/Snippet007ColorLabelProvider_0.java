
	static class ColorLabelProvider extends ObservableMapLabelProvider implements ITableColorProvider {
		private Color maleColor;
		private Color femaleColor;
		private List<Person> persons;

		ColorLabelProvider(IObservableMap<?, ?>[] attributes, Display display, List<Person> persons) {
			super(attributes);
			this.persons = persons;
			this.maleColor = new Color(display, 255, 192, 203);
			this.femaleColor = display.getSystemColor(SWT.COLOR_BLUE);
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
			return person.getGender() == Gender.MALE ? maleColor : femaleColor;
		}

		@Override
		public void dispose() {
			super.dispose();
			femaleColor.dispose();
		}
	}
