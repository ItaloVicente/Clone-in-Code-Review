					 if (Constants.GITIGNORE_FILENAME.equals(resource.getName())) {
                         try {
                                 mapping.refreshIgnoreNode(resource);
                         } catch (IOException e) {
                                 handleException(resource, new CoreException(new Status(IStatus.ERROR, Activator.getPluginId(),
                                                 e.getMessage())));
                         }
                         resourcesToUpdate.addAll(Arrays.asList(resource.getParent().members()));
					 } else if (Constants.EXCLUDE_FILENAME.equals(resource.getName()) && resource.getParent().getParent().getName().equals(Constants.DOT_GIT)) {
                         try {
                                 mapping.refreshBase();
                         } catch (IOException e) {
                                 handleException(resource, new CoreException(new Status(IStatus.ERROR, Activator.getPluginId(),
                                                 e.getMessage())));
                         }
                         resourcesToUpdate.addAll(Arrays.asList(resource.getProject().members()));
					 } else {
                         resourcesToUpdate.add(resource);
					 }
