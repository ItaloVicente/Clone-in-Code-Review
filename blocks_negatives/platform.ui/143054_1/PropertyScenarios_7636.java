        Text text = new Text(getComposite(), SWT.BORDER);

        IObservableValue defaultLodging = BeansObservables.observeDetailValue(
        		BeansObservables.observeValue(adventure, "defaultLodging"),
        		"description", String.class);

        getDbc().bindValue(SWTObservables.observeText(text, SWT.Modify), defaultLodging);

        assertEquals(adventure.getDefaultLodging().getDescription(), text.getText());
        enterText(text, "foobar");
        assertEquals("foobar", adventure.getDefaultLodging().getDescription());
        adventure.getDefaultLodging().setDescription("barfoo");
        assertEquals(adventure.getDefaultLodging().getDescription(), text.getText());

        adventure.setDefaultLodging(SampleData.CAMP_GROUND);
        assertEquals(adventure.getDefaultLodging().getDescription(), text.getText());
        adventure.getDefaultLodging().setDescription("barfo");
        assertEquals(adventure.getDefaultLodging().getDescription(), text.getText());

        adventure.setDefaultLodging(null);
        assertEquals("", text.getText());

        adventure.setDefaultLodging(SampleData.FIVE_STAR_HOTEL);
        assertEquals(adventure.getDefaultLodging().getDescription(), text.getText());
        adventure.getDefaultLodging().setDescription("barf");
        assertEquals(adventure.getDefaultLodging().getDescription(), text.getText());

    }

    @Test
