        Text text = getTextControl();
        if (text != null) {
            int value = getPreferenceStore().getDefaultInt(getPreferenceName());
        }
        valueChanged();
    }

    @Override
