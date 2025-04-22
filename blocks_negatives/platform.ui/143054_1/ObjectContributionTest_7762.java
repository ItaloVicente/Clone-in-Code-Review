        assertPopupMenus("6",
                new String[] {"ResourceMapping.1"},
                new StructuredSelection(new Object[] {new ObjectContributionClasses.CFile(), new ObjectContributionClasses.CResource()}),
                ResourceMapping.class,
                true
            );
        assertPopupMenus("7",
                new String[] {"ResourceMapping.1", "IResource.1"},
                new StructuredSelection(new Object[] {new ObjectContributionClasses.ModelElement()}),
                ResourceMapping.class,
                true
            );
        assertPopupMenus("8",
                new String[] {"ResourceMapping.1", "IResource.1"},
                new StructuredSelection(new Object[] {new ObjectContributionClasses.CResourceOnly()}),
                ResourceMapping.class,
                true
            );
    }
