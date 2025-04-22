        create();
        Button b = getButton(defaultButtonIndex);
        b.setFocus();
        b.getShell().setDefaultButton(b);
        return super.open();
    }

    /**
     * Set the detail button;
     * @param index the detail button index
     */
    public void setDetailButton(int index) {
        detailButtonID = index;
    }

    @Override
