        }

        if (radioButtons.length > 0) {
            radioButtons[0].setSelection(true);
            this.value = (String) radioButtons[0].getData();
        }
        return;
    }

    /*
     * @see FieldEditor.setEnabled(boolean,Composite).
     */
    @Override
