    /**
     * Returns the given <code>prefNode</code>, but only if it's no
     * WorkbenchPreferenceExtensionNode which fails the Expression check.
     *
     * @param prefNode
     *            The preference node which will be checked. Can be <code>null
     *            </code>.
     * @return The given <code>prefNode</code>, or <code>null</code> if it
     *         fails the Expressions check.
     */
    public static IPreferenceNode getNodeExpression(
    		IPreferenceNode prefNode) {
    	if (prefNode == null)
    		return null;
        if (prefNode instanceof WorkbenchPreferenceExtensionNode) {
        	WorkbenchPreferenceExpressionNode node =
        		(WorkbenchPreferenceExtensionNode)prefNode;
            if (WorkbenchActivityHelper.restrictUseOf(node)) {
                return null;
            }
        }
        return prefNode;
    }
