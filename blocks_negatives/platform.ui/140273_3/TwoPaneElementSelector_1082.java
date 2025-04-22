        return null;
    }

    private void updateLowerListWidget(Object[] elements) {
        int length = elements.length;
        String[] qualifiers = new String[length];
        for (int i = 0; i != length; i++){
        	String text = fQualifierRenderer.getText(elements[i]);
        	if(text == null) {
