        Button checkbox = new Button(getComposite(), SWT.CHECK);
        adventure.setPetsAllowed(true);

        getDbc().bindValue(SWTObservables.observeSelection(checkbox),
                BeansObservables.observeValue(adventure, "petsAllowed"));

        assertEquals(true, checkbox.getSelection());
        setButtonSelectionWithEvents(checkbox, false);
        assertEquals(false, adventure.isPetsAllowed());
        adventure.setPetsAllowed(true);
        assertEquals(true, checkbox.getSelection());
    }

    @Test
