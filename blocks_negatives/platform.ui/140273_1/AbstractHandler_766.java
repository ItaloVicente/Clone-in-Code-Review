public abstract class AbstractHandler extends
        org.eclipse.core.commands.AbstractHandler implements IHandler {

    /**
     * Those interested in hearing about changes to this instance of
     * <code>IHandler</code>. This member is null iff there are
     * no listeners attached to this handler. (Most handlers don't
     * have any listeners, and this optimization saves some memory.)
     */
