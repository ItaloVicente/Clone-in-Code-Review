 * Example of creating and running a workbench (in an
 * <code>IPlatformRunnable</code>):
 * </p>
 *
 * <pre>
 * <code>
 *           public class MyApplication implements IPlatformRunnable {
 *             public Object run(Object args) {
 *               WorkbenchAdvisor workbenchAdvisor = new MyWorkbenchAdvisor();
 *               Display display = PlatformUI.createDisplay();
 *               int returnCode = PlatformUI.createAndRunWorkbench(display, workbenchAdvisor);
 *               if (returnCode == PlatformUI.RETURN_RESTART) {
 *                  return IPlatformRunnable.EXIT_RESTART;
 *               } else {
 *                  return IPlatformRunnable.EXIT_OK;
 *             }
 *           }
 * </code>
 * </pre>
 *
