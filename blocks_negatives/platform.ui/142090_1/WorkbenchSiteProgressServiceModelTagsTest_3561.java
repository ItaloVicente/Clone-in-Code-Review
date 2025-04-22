    	public void showBusy(boolean busy) {
    		super.showBusy(busy);
    	}
    }

    private void assertModelTagChangedEvent(Event event) {
    	assertNotNull(event);
    	assertTrue(event.getProperty(UIEvents.EventTags.ELEMENT) instanceof MPart);
    	assertEquals(UIEvents.ApplicationElement.TAGS, event.getProperty(UIEvents.EventTags.ATTNAME));
    }

    private void assertAddBusyTagEvent(Event event) {
    	assertModelTagChangedEvent(event);
    	assertNull(event.getProperty(UIEvents.EventTags.OLD_VALUE));
    	assertEquals(CSSConstants.CSS_BUSY_CLASS, event.getProperty(UIEvents.EventTags.NEW_VALUE));
    }

    private void assertRemoveBusyTagEvent(Event event) {
    	assertModelTagChangedEvent(event);
    	assertEquals(CSSConstants.CSS_BUSY_CLASS, event.getProperty(UIEvents.EventTags.OLD_VALUE));
    	assertNull(event.getProperty(UIEvents.EventTags.NEW_VALUE));
    }

    private void assertContentChangeTagEvent(Event event) {
    	assertModelTagChangedEvent(event);

    	assertTrue(CSSConstants.CSS_CONTENT_CHANGE_CLASS.equals(event.getProperty(UIEvents.EventTags.OLD_VALUE)) ||
    			CSSConstants.CSS_CONTENT_CHANGE_CLASS.equals(event.getProperty(UIEvents.EventTags.NEW_VALUE)));
    }
