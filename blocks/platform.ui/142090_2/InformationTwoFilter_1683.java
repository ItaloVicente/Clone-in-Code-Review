		if (object instanceof TreeNode) {
			Element element = (Element) ((TreeNode) object).getValue();
			if (element instanceof Information) {
				Information information = (Information) element;
				if (information.getName().contains("Two")) {//$NON-NLS-1$
					return true;
				}
			}
		}
		return false;
	}
