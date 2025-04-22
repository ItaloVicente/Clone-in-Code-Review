                handCursor.dispose();
                handCursor = null;
                busyCursor.dispose();
                busyCursor = null;
            }
        });
    }

	
    /**
     * Adds listeners to the given styled text
     */
    protected void addListeners() {
        styledText.addMouseListener(new MouseAdapter() {
            @Override
