        suite.addTest(new ObjectContributionsPerformance(
                "large selection, limited contributors",
                generateAdaptableSelection(SEED, 5000),
                BasicPerformanceTest.NONE));
        suite
                .addTest(new ObjectContributionsPerformance(
                        "limited selection, limited contributors",
                        generateAdaptableSelection(SEED, 50),
                        BasicPerformanceTest.NONE));
        return suite;
