 * This class shows how <code>IActionDelegate</code> implementations
 * should be used for global action registration for menu
 * and tool bars. Action proxy object is created in the
 * workbench based on presentation information in the plugin.xml
 * file. Delegate is not loaded until the first time the user
 * presses the button or selects the menu. Based on the action
 * availability, it is possible that the button will disable
 * instead of executing.
