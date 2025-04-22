 *   <li>A predefined perspective is defined by an extension to the workbench's
 *     perspective extension point (<code>"org.eclipse.ui.perspectives"</code>).
 *     The extension defines a id, label, and <code>IPerspectiveFactory</code>.
 *     A perspective factory is used to define the initial layout for a page.
 *     </li>
 *   <li>A custom perspective is defined by the user.  In this case a predefined
 *     perspective is modified to suit a particular task and saved as a new
 *     perspective.  The attributes for the perspective are stored in a separate file
 *     in the workbench's metadata directory.
 *     </li>
