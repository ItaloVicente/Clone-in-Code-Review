public class CSSSWTFontHelperTest extends CSSSWTHelperTestCase {	
	private Display display;
	
	@Override
	protected void setUp() throws Exception {
		display = Display.getDefault();
	}
	
	public void testHasFontDefinitionAsFamily() throws Exception {
		boolean result = hasFontDefinitionAsFamily(fontProperties(addFontDefinitionMarker("org-eclipse-wst-sse-ui-textfont"), 10, SWT.NORMAL));
		
		assertTrue(result);
	}
	
	public void testHasFontDefinitionAsFamilyWhenNotDefinitionAsFamily() throws Exception {
		boolean result = hasFontDefinitionAsFamily(fontProperties("arial", 10, SWT.NORMAL));
		
		assertFalse(result);
	}
	
