    	Control[] children = getShell().getChildren();
    	Button button = (Button) children[0];
    	assertEquals("", button.getText());
    	action.setText("Text");
    	assertEquals("", button.getText());
    	item.setMode(ActionContributionItem.MODE_FORCE_TEXT);
    	assertEquals("Text", button.getText());
    	action.setText(null);
    	assertEquals("", button.getText());
    }
