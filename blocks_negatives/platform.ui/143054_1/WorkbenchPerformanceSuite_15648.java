        addTest(new ObjectContributionsPerformance(
                "large selection, limited contributors",
                ObjectContributionsPerformance.generateAdaptableSelection(
                        ObjectContributionsPerformance.SEED, 5000),
                BasicPerformanceTest.NONE));
        addTest(new ObjectContributionsPerformance(
                "limited selection, limited contributors",
                ObjectContributionsPerformance.generateAdaptableSelection(
                        ObjectContributionsPerformance.SEED, 50),
                BasicPerformanceTest.NONE));
