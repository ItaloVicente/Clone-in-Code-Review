            if (selection.size() == 1) {
                Object object = selection.getFirstElement();
                if (object instanceof IProject) {
                    enable = ((IProject) object).isOpen();
                } else if (object instanceof IFolder) {
                    enable = true;
                }
            }
        }
        goIntoAction.setEnabled(enable);
    }
