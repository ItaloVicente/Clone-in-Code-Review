        button.dispose();
        button = new Button(getComposite(), SWT.RADIO);

        getDbc().bindValue(SWTObservables.observeSelection(button),
                BeansObservables.observeValue(adventure, "petsAllowed"));

        assertEquals(button.getSelection(), adventure.isPetsAllowed());
        boolean newBoolean = !adventure.isPetsAllowed();
        adventure.setPetsAllowed(newBoolean);
        assertEquals(newBoolean, adventure.isPetsAllowed());
        assertEquals(button.getSelection(), newBoolean);
        newBoolean = !newBoolean;
        button.setSelection(newBoolean);
        button.notifyListeners(SWT.Selection, null);
        assertEquals(newBoolean, adventure.isPetsAllowed());
    }
