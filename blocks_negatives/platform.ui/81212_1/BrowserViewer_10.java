        browser.addTitleListener(new TitleListener() {
            @Override
			public void changed(TitleEvent event) {
					 String oldTitle = title;
                title = event.title;
					 firePropertyChangeEvent(PROPERTY_TITLE, oldTitle, title);
            }
        });
