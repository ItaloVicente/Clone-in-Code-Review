        assertDecorated("1",
                new String[] {TestAdaptableDecoratorContributor.SUFFIX},
                new Object[] {
                        new ObjectContributionClasses.Common(),
                        new ObjectContributionClasses.C(),
                        new ObjectContributionClasses.B(),
                        new ObjectContributionClasses.A()
                },
                true
            );
        assertDecorated("2",
                new String[] {TestAdaptableDecoratorContributor.SUFFIX},
                new Object[] {
                        new Object()
                },
                false
            );
    }

    /**
     * Test non-adaptable contributions
     *
     * @since 3.1
     */
