        if (!oleActivated) {
            clientSite.doVerb(OLE.OLEIVERB_SHOW);
            oleActivated = true;
            String progId = clientSite.getProgramID();
                handleWord();
            }
        }
