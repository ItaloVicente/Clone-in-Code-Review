            IFolder folder = getFolder(newDestination);
            if (folder != null) {
                if (policy != POLICY_FORCE_OVERWRITE) {
                    if (this.overwriteState == OVERWRITE_NONE
                            || !queryOverwrite(newDestinationPath)) {
                        noOverwrite.add(folder);
                        continue;
                    }
                }
                if (provider.isFolder(nextSource)) {
