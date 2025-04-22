	 * @param <T>
	 *            The generic type of the returned list
	 * @param searchRoot
	 *            The element at which to start the search. This element must be non-null and is
	 *            included in the search.
	 * @param clazz
	 *            The type of element to be searched for. If non-null this also defines the return
	 *            type of the List.
	 * @param searchFlags
	 *            A bitwise combination of the following constants:
	 *            <ul>
	 *            <li><b>OUTSIDE_PERSPECTIVE</b> Include the elements in the window's model that are
	 *            not in a perspective</;i>
	 *            <li><b>IN_ANY_PERSPECTIVE</b> Include the elements in all perspectives</;i>
	 *            <li><b>IN_ACTIVE_PERSPECTIVE</b> Include the elements in the currently active
	 *            perspective only</;i>
	 *            <li><b>IN_MAIN_MENU</b> Include elements in an MWindow's main menu</;i>
	 *            <li><b>IN_PART</b> Include MMenu and MToolbar elements owned by parts</;i>
	 *            <li><b>IN_ACTIVE_PERSPECTIVE</b> Include the elements in the currently active
	 *            perspective only</;i>
	 *            <li><b>IN_SHARED_AREA</b> Include the elements in the shared area</;i>
	 *            <li><b>IN_TRIM</b> Include the elements in the window's trim</;i>
	 *            </ul>
	 *            Note that you may omit both perspective flags but still define
	 *            <b>IN_SHARED_AREA</b>; the flags <b>OUTSIDE_PERSPECTIVE | IN_SHARED_AREA</b> for
	 *            example will search the presentation <i>excluding</i> the elements in perspective
	 *            stacks.
	 * @param matcher
	 *            An implementation of a Selector that will return true for elements that it wants
	 *            in the returned list.
