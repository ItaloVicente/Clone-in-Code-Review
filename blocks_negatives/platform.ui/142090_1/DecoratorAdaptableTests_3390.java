                    allMatchesFound = false;
                }
            }
            assertTrue("Adaptable test " + testSubName + " has failed for object " + object.toString(), allMatchesFound == shouldHaveMatches);
        }
    }

    @Override
