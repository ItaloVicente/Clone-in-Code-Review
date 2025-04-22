 * as they are now passed an <code>IPageSite</code> during their initialization 
 * and this site can be configured with a selection provider. 
 * However to avoid a breaking change 
 *  1) this interface will continue to extend ISelectionProvider 
 *  2) if an IContentOutlinePage does not set a selection provider for its 
 * site, the ContentOutline will continue to use the page itself for 
 * this purpose. 
 * </p> 
