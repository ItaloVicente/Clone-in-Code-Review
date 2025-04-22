                        build.doBuild();
                    } else {
                        IProject[] projects = new IProject[selection.length];
                        for (int i = 0; i < selection.length; i++) {
                            projects[i] = (IProject) selection[i];
                        }

                        ProjectSubsetBuildAction projectBuild =
                            new ProjectSubsetBuildAction(window,
                                IncrementalProjectBuilder.INCREMENTAL_BUILD,
                                projects);
                        projectBuild.runInBackground(
                                ResourcesPlugin.getWorkspace().getRuleFactory().buildRule());
                    }
                }
                return Status.OK_STATUS;
            }
        };
