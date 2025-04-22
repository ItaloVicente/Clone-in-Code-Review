		setFriends(stan, new Person[] { kyle, eric, kenny, wendy });
		setFriends(kyle, new Person[] { stan, eric, kenny });
		setFriends(eric, new Person[] { eric });
		setFriends(kenny, new Person[] { stan, kyle, eric });
		setFriends(wendy, new Person[] { stan });
		setFriends(butters, new Person[0]);

		Person[] people = new Person[] { stan, kyle, eric, kenny, wendy, butters };
		viewModel.setPeople(Arrays.asList(people));
