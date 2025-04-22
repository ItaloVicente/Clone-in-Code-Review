        if (event.getProperty().equals(FieldEditor.IS_VALID)) {
            boolean newValue = ((Boolean) event.getNewValue()).booleanValue();
            if (newValue) {
                checkState();
            } else {
                invalidFieldEditor = (FieldEditor) event.getSource();
                setValid(newValue);
            }
        }
    }

    @Override
