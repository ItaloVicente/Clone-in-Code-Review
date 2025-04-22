	private IMenuListener2 showHelper;

	private IMenuListener2 hideHelper;

	private static MenuManagerEventHelper INSTANCE;
	
	public static MenuManagerEventHelper getInstance() {
		if( INSTANCE == null ) {
			INSTANCE = new MenuManagerEventHelper();
		}
		return INSTANCE;
	}
	
