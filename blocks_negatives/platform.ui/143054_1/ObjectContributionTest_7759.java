    /**
     * Tests whether the content-type object contribution works. This is testing
     * a use care familiar to Ant UI. The content-type scans an XML file to see
     * if its root element is <code>&lt;project&gt;</code>.
     *
     * @throws CoreException
     *             If a problem occurs when creating the project or file, or if
     *             the project can't be opened.
     */
    public final void testObjectStateContentType() throws CoreException {
        final IWorkspace workspace = ResourcesPlugin.getWorkspace();
        final IProject testProject = workspace.getRoot().getProject(
                "ObjectContributionTestProject");
        testProject.create(null);
        testProject.open(null);
        final IFile xmlFile = testProject.getFile("ObjectContributionTest.xml");
        final String contents = "<testObjectStateContentTypeElement></testObjectStateContentTypeElement>";
        final ByteArrayInputStream inputStream = new ByteArrayInputStream(
                contents.getBytes());
        xmlFile.create(inputStream, true, null);
        final ISelection selection = new StructuredSelection(xmlFile);
        assertPopupMenus("1", new String[] {"org.eclipse.ui.tests.testObjectStateContentType"}, selection, null, true);
    }
