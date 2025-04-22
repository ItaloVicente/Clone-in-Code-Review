			@Override
			public boolean test() throws Exception {
				return tree.hasItems();
			}

			@Override
			public String getFailureMessage() {
				return "No items appeared in 'Add Existing Local Git Repository' dialog";
			}
		});
