        switch (name) {
        case IS_EMPTY:
        	return sel.getLength() == 0;
        case TEXT:
        {
        	String text = sel.getText();
        	return text.contains(value);
        }
        case CASE_INSENSITIVE_TEXT:
        {
        	String text = sel.getText().toLowerCase();
        	value = value.toLowerCase();
        	return text.contains(value);
        }
        default:
        	break;
