        assertDecorated("1",
                new String[] {TestUnadaptableDecoratorContributor.SUFFIX},
                new Object[] {
                        new ObjectContributionClasses.A(),
                        new ObjectContributionClasses.B()},
                false
            );
        assertDecorated("2",
                new String[] {TestUnadaptableDecoratorContributor.SUFFIX},
                new Object[] {
                        new ObjectContributionClasses.D(),
                        new ObjectContributionClasses.C(),
                        new ObjectContributionClasses.Common()},
                true
            );
    }

    /**
     * This tests backwards compatibility support for adaptable IResource objectContributions. This
     * allows IResource adaptable contributions without an adapter factory and using
     * the IContributorResourceAdapter factory. In addition, test the ResourceMapping adaptations.
     *
     * @since 3.1
     */
    public final void testContributorResourceAdapter() throws CoreException {

        final IWorkspace workspace = ResourcesPlugin.getWorkspace();
        final IProject testProject = workspace.getRoot().getProject(ObjectContributionClasses.PROJECT_NAME);
        if(! testProject.exists()) {
            testProject.create(null);
        }
        if(! testProject.isOpen()) {
            testProject.open(null);
        }

        assertDecorated("1",
                new String[] {"IResource.1"},
                new Object[] {
                    new ObjectContributionClasses.CResource(),
                    new ObjectContributionClasses.CFile()},
                true
            );

        assertDecorated("2",
                new String[] {"ResourceMapping.1"},
                new Object[] {
                        new ObjectContributionClasses.CFile(),
                        new ObjectContributionClasses.CResource()},
                true
            );
        assertDecorated("3",
                new String[] {"ResourceMapping.1", "IResource.1"},
                new Object[] {
                    new ObjectContributionClasses.ModelElement()},
                true
            );
        assertDecorated("4",
                new String[] {"ResourceMapping.1", "IResource.1"},
                new Object[] {
                    new ObjectContributionClasses.CResourceOnly()},
                true
            );
    }
