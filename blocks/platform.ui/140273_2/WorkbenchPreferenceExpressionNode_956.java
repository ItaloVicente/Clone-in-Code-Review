	public static IPreferenceNode getNodeExpression(IPreferenceNode prefNode) {
		if (prefNode == null)
			return null;
		if (prefNode instanceof WorkbenchPreferenceExtensionNode) {
			WorkbenchPreferenceExpressionNode node = (WorkbenchPreferenceExtensionNode) prefNode;
			if (WorkbenchActivityHelper.restrictUseOf(node)) {
				return null;
			}
		}
		return prefNode;
	}
