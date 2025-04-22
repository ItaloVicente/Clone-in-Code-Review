		processEventsUntil(new Condition() {
			@Override
			public boolean compute() {
				return table.getItemCount() != newCount;
			};
		}, 200);
