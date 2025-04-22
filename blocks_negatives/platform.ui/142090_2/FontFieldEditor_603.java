            changeFontButton.addDisposeListener(event -> changeFontButton = null);
            changeFontButton.setFont(parent.getFont());
            setButtonLayoutData(changeFontButton);
        } else {
            checkParent(changeFontButton, parent);
        }
        return changeFontButton;
    }

    @Override
