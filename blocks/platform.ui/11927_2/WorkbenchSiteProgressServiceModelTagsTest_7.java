    }
    
    private void assertContentChangeTagEvent(Event event) {
    	assertModelTagChangedEvent(event);
    	
    	assertTrue(CSSConstants.CSS_CONTENT_CHANGE_CLASS.equals(event.getProperty(UIEvents.EventTags.OLD_VALUE)) ||
    			CSSConstants.CSS_CONTENT_CHANGE_CLASS.equals(event.getProperty(UIEvents.EventTags.NEW_VALUE)));
