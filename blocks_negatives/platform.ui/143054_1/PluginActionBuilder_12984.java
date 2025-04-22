            IMenuManager parent = menu;
            if (mpath != null) {
                parent = parent.findMenuUsingPath(mpath);
                if (parent == null) {
                    return;
                }
            }

            if (mgroup == null) {
