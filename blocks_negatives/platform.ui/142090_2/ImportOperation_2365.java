            IFolder folder = workspace.getRoot().getFolder(resourcePath);
            if (createVirtualFolder || createLinks || folder.isVirtual() || folder.isLinked()) {
                folder.delete(true, null);
            } else
                return POLICY_FORCE_OVERWRITE;
        }
