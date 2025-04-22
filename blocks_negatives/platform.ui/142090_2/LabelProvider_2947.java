        if (obj == null || obj.equals(StructuredSelection.EMPTY)) {
            return null;
        }
        if (obj instanceof IStructuredSelection) {
            IStructuredSelection structuredSelection = (IStructuredSelection) obj;
            if (areDifferentTypes(structuredSelection)) {
                return null;
            }
            obj = structuredSelection.getFirstElement();
        }
        Element element = (Element) ((TreeNode) obj).getValue();
        return element.getImage();
    }
