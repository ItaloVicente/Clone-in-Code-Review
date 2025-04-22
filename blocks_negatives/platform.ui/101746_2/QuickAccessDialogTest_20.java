		processEventsUntil(new Condition() {
			@Override
			public boolean compute() {
				return table.getItemCount() != oldCount;
			};
		}, 200);
