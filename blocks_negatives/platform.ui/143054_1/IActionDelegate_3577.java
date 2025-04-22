 * This interface should be implemented by clients who need to contribute actions
 * via an extension point. The workbench will generate a <b>proxy action</b>
 * object on behalf of the plug-in to avoid having to activate the plug-in until
 * the user needs it. If the action is performed the workbench will load the class
 * that implements this interface and create what is called an <b>action
 * delegate</b> object. Then the request, and all subsequent ones, are
 * forwarded through the proxy action to the action delegate, which does the
