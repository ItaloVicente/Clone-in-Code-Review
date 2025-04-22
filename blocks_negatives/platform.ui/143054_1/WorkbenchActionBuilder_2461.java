		    }
        }
    }

    /**
     * Returns whether any of the given infos have a welcome page.
     *
     * @param infos the infos
     * @return <code>true</code> if a welcome page was found, <code>false</code> if not
     */
    private boolean hasWelcomePage(AboutInfo[] infos) {
        for (AboutInfo info : infos) {
            if (info.getWelcomePageURL() != null) {
            	return true;
            }
        }
        return false;
