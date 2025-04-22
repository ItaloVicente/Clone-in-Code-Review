        super.setInput(part, selection);
        Assert.isTrue(selection instanceof IStructuredSelection);
        Object input = ((IStructuredSelection) selection).getFirstElement();
        Assert.isTrue(input instanceof TreeNode);
        this.treeNode = (TreeNode) input;
    }
