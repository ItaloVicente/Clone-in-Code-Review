 * <li> All of the matching contributors will be invoked per property lookup
 * <li> The search order from a class with the definition<br>
 *			<code>class X extends Y implements A, B</code><br>
 *		is as follows:
 * 		<il>
 *			<li>the target's class: X
 *			<li>X's superclasses in order to <code>Object</code>
 *			<li>a depth-first traversal of the target class's interaces in the order
 *				returned by <code>getInterfaces()</code> (in the example, A and
 *				its superinterfaces then B and its superinterfaces)
 *		</il>
