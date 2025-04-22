        Listener listener = new Listener() {
            @Override
			public void handleEvent(Event e) {
                fFilteredList.setFilter(fFilterText.getText());
            }
        };
