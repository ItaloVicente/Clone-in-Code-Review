		processEventsUntil(new Condition() {
			@Override
			public boolean compute() {
				return table.getItemCount() > 1;
			};
		}, 200);
