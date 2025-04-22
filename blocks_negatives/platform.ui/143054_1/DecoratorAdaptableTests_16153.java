        for (Object object : elements) {
            String text = getDecorationTextFor(object);
            boolean allMatchesFound = true;
            for (String suffix : expectedSuffixes) {
                if (text.indexOf(suffix) == -1) {
                    allMatchesFound = false;
                }
            }
            assertTrue("Adaptable test " + testSubName + " has failed for object " + object.toString(), allMatchesFound == shouldHaveMatches);
        }
    }

    @Override
