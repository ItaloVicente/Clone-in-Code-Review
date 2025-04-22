        if (object instanceof TreeNode) {
            Element element = (Element) ((TreeNode) object).getValue();
            if (element instanceof Information) {
                Information information = (Information) element;
                    return true;
                }
            }
        }
        return false;
    }
