    /**
     *	Specify the passed wizard node as being selected, meaning that if
     *	it's non-null then the wizard to be displayed when the user next
     *	presses the Next button should be determined by asking the passed
     *	node.
     *
     *	@param node org.eclipse.jface.wizards.IWizardNode
     */
    public void selectWizardNode(IWizardNode node) {
        setSelectedNode(node);
    }
