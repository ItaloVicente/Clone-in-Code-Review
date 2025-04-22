 * The workbench will retrieve the filter from the selected object by testing to see
 * if it implements <code>IActionFilter</code>.  If that fails, the workbench will ask for
 * a filter through through the <code>IAdaptable</code> mechanism.  If a filter is
 * found the workbench will pass each name value pair to the filter to determine if it
 * matches the state of the selected object.  If so, or there is no filter, the action
 * will be added to the context menu for the object.
