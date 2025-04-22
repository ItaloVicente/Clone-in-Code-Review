public final class OpenPerspectiveAction extends Action implements
        IPluginContribution {

    /**
     * The perspective menu that will handle the execution of this action. This
     * allows subclasses of <code>PerspectiveMenu</code> to define custom
     * behaviour for these actions. This value should not be <code>null</code>.
     */
    private final PerspectiveMenu callback;
