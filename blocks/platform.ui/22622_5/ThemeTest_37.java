    private void mockCSSTheme() {
    	IThemeEngine themeEngine = (IThemeEngine) fWorkbench.getService(IThemeEngine.class);
        org.eclipse.e4.ui.css.swt.theme.ITheme currentTheme = themeEngine.getActiveTheme(); 
        if (currentTheme != null && !MOCK_CSS_THEME.equals(currentTheme.getId())) {
        	themeEngine.setTheme(MOCK_CSS_THEME, false);
        }
    }
    
