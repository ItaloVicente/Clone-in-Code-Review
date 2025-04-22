		stan.setFriends(kyle, eric, kenny, wendy);
		kyle.setFriends(stan, eric, kenny);
		eric.setFriends(eric);
		kenny.setFriends(stan, kyle, eric);
		wendy.setFriends(stan);
		butters.setFriends();

		viewModel.setPeople(Arrays.asList(stan, kyle, eric, kenny, wendy, butters));
