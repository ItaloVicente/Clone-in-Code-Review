        ITextSelection sel = (ITextSelection) target;
        if (name.equals(IS_EMPTY)) {
            return (sel.getLength() == 0);
        } else if (name.equals(TEXT)) {
            String text = sel.getText();
            return (text.contains(value));
        } else if (name.equals(CASE_INSENSITIVE_TEXT)) {
            String text = sel.getText().toLowerCase();
            value = value.toLowerCase();
            return (text.contains(value));
        }
        return false;
    }
