		public ArrayList<Person> getPeople() {
			return people;
		}

		public ArrayList<String> getCities() {
			return cities;
		}
	}

	/** The GUI view. */
	static class View {
		private ViewModel viewModel;

		public View(ViewModel viewModel) {
			this.viewModel = viewModel;
		}

