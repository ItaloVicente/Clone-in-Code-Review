    public void testInsert() {
    	ListViewer v = ((ListViewer)fViewer);
    	TestElement element = new TestElement(fModel, fRootElement);
    	v.insert(element, 1);
    	assertSame("test insert", element, v.getElementAt(1));
    	assertEquals("test insert", element.toString(), v.getList().getItem(1));
