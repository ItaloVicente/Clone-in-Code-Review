 * 	<li>When a part extension is created:
 *    <ul>
 *		<li>instantiate the part</li>
 *		<li>create a part site</li>
 *		<li>call <code>part.init(site)</code></li>
 * 	  </ul>
 *  <li>When a part becomes visible in the workbench:
 * 	  <ul>
 *		<li>add part to presentation by calling
 *        <code>part.createPartControl(parent)</code> to create actual widgets</li>
 *		<li>fire <code>partOpened</code> event to all listeners</li>
 *	  </ul>
 *   </li>
 *  <li>When a part is activated or gets focus:
 *    <ul>
 *		<li>call <code>part.setFocus()</code></li>
 *		<li>fire <code>partActivated</code> event to all listeners</li>
 *	  </ul>
 *   </li>
 *  <li>When a part is closed:
 *    <ul>
 *		<li>if save is needed, do save; if it fails or is canceled return</li>
 *		<li>if part is active, deactivate part</li>
 *		<li>fire <code>partClosed</code> event to all listeners</li>
 *		<li>remove part from presentation; part controls are disposed as part
 *         of the SWT widget tree
 *		<li>call <code>part.dispose()</code></li>
 *	  </ul>
 *   </li>
