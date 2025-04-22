 * the initial page layout.  If this is a predefined perspective (based on an extension to
 * the workbench's perspective extension point) an <code>IPerspectiveFactory</code>
 * is used to define the initial page layout.
 * </p><p>
 * The factory for the perspective is created and passed an <code>IPageLayout</code>
 * where views can be added.  The default layout consists of the editor area with no
 * additional views.  Additional views are added to the layout using
 * the editor area as the initial point of reference.  The factory is used only briefly
 * while a new page is created; then discarded.
 * </p><p>
 * To define a perspective clients should implement this interface and
 * include the name of their class in an extension to the workbench's perspective
 * extension point (named <code>"org.eclipse.ui.perspectives"</code>).  For example,
 * the plug-in's XML markup might contain:
