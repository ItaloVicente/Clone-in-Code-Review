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
        for (int i = 0; i < infos.length; i++) {
            if (infos[i].getWelcomePageURL() != null) {
            	return true;
            }
        }
        return false;
