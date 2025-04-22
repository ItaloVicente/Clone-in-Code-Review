        String newValue = textField.getText();
        if (!newValue.equals(oldValue)) {
            fireValueChanged(VALUE, oldValue, newValue);
            oldValue = newValue;
        }
    }

    /*
     * @see FieldEditor.setEnabled(boolean,Composite).
     */
    @Override
