 * In some situations the object class and name are not enough to describe the intended
 * target action.  In those situations an action extension may define one or more
 * <code>filter</code> sub-elements.  Each one of these elements describes one attribute of
 * the action target using a <code>name value</code> pair.  The attributes for an object
 * are type specific and beyond the domain of the workbench itself, so the workbench
 * will delegate filtering at this level to an <code>IActionFilter</code>.  This is a
 * filtering strategy which is provided by the selection itself and may perform type
 * specific filtering.
