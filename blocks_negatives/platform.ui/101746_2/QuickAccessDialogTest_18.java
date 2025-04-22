		processEventsUntil(new Condition() {
			@Override
			public boolean compute() {
				return table.getItemCount() > 1 && !table.getItem(0).getText(1).equals(oldFirstItemText);
			};
		}, 200);
