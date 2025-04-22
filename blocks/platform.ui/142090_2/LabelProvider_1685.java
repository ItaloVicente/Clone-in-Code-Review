		if (obj == null || obj.equals(StructuredSelection.EMPTY)) {
			return null;
		}
		int size = 1;
		if (obj instanceof IStructuredSelection) {
			IStructuredSelection structuredSelection = (IStructuredSelection) obj;
			if (areDifferentTypes(structuredSelection)) {
				return structuredSelection.size() + " items selected";//$NON-NLS-1$
			}
			obj = structuredSelection.getFirstElement();
			size = structuredSelection.size();
		}
		StringBuilder ret = new StringBuilder();
		Element element = (Element) ((TreeNode) obj).getValue();
		String type = element.getClass().getName();
		String name = element.getName();
		ret.append('\u00AB');
		ret.append(type.substring(type.lastIndexOf('.') + 1));
		ret.append('\u00BB');
		if (size == 1) {
			ret.append(' ');
			ret.append(name);
		} else {
			ret.append(' ');
			ret.append(Integer.toString(size));
			ret.append(" selected");//$NON-NLS-1$
		}
		return ret.toString();
	}
