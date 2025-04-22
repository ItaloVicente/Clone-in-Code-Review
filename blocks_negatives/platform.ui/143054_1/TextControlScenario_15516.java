        text.setText("4");
        assertEquals(4, adventure.getMaxNumberOfPeople());
        text.setText("999");
        assertEquals(4, adventure.getMaxNumberOfPeople());
        dbc.dispose();
    }
