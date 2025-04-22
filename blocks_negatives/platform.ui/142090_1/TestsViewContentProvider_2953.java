        if (parent.equals(this.view.getViewSite())) {
            if (invisibleRoot == null)
                initialize();
            return getChildren(invisibleRoot);
        }
        return getChildren(parent);
    }
