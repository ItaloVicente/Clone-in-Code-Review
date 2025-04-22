        Label label = new Label(getComposite(), SWT.NONE);
        getDbc().bindValue(SWTObservables.observeText(text, SWT.Modify), SWTObservables.observeText(label));

        String newTextValue = "Frog";
        for (int i = 0; i < newTextValue.length(); i++) {
            text.setText(newTextValue.substring(0, i + 1));
            assertEquals(text.getText(), label.getText());
        }
        text.notifyListeners(SWT.FocusOut, null);
        assertEquals(text.getText(), label.getText());
    }
