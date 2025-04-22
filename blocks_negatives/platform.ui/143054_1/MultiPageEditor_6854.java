 *   <li><code>setInitializationData</code> - extend to provide additional
 *       initialization when editor extension is instantiated</li>
 *   <li><code>init(IEditorSite,IEditorInput)</code> - extend to provide
 *       additional initialization when editor is assigned its site</li>
 *   <li><code>isSaveOnCloseNeeded</code> - override to control saving</li>
 *   <li><code>isSaveAsAllowed</code> - override to control saving</li>
 *   <li><code>gotoMarker</code> - reimplement to make selections based on
 *       markers</li>
 *   <li><code>dispose</code> - extend to provide additional cleanup</li>
 *   <li><code>getAdapter</code> - reimplement to make their editor
 *       adaptable</li>
