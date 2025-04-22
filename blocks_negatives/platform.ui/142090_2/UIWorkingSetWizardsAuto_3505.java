                .getControl(), Tree.class);
        Tree tree = (Tree) widgets.get(0);
        TreeItem[] treeItems = tree.getItems();
        for (TreeItem treeItem : treeItems) {
            treeItem.setChecked(true);
            Event event = new Event();
            event.detail = SWT.CHECK;
            event.item = treeItem;
            tree.notifyListeners(SWT.Selection, event);
        }
    }

    private void deleteResources() throws CoreException {
        try {
            if (p1 != null) {
                FileUtil.deleteProject(p1);
            }
            if (p2 != null) {
                FileUtil.deleteProject(p2);
            }

        } catch (CoreException e) {
            TestPlugin.getDefault().getLog().log(e.getStatus());
            fail();
            throw (e);

        }

    }

    private Shell getShell() {
        return DialogCheck.getShell();
    }
