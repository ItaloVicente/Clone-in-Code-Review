        switch (name) {
        case IS_EMPTY:
        	return (sel.getLength() == 0);
        case TEXT:
        {
        	String text = sel.getText();
        	return (text.indexOf(value) >= 0);
        }
        case CASE_INSENSITIVE_TEXT:
        {
        	String text = sel.getText().toLowerCase();
        	value = value.toLowerCase();
        	return (text.indexOf(value) >= 0);
        }
        default:
        	break;
