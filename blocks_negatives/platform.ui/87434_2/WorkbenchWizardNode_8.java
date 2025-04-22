                        SafeRunner.run(new SafeRunnable() {
                            /**
                             * Add the exception details to status is one happens.
                             */
                            @Override
							public void handleException(Throwable e) {
                               	IPluginContribution contribution = Adapters.adapt(wizardElement, IPluginContribution.class);
                                statuses[0] = new Status(
                                        IStatus.ERROR,
                                        contribution != null ? contribution.getPluginId() : WorkbenchPlugin.PI_WORKBENCH,
                                        IStatus.OK,
                                        WorkbenchMessages.WorkbenchWizard_errorMessage,
                                        e);
                            }

                            @Override
							public void run() {
                                try {
                                    workbenchWizard[0] = createWizard();
                                } catch (CoreException e) {
                                	IPluginContribution contribution = Adapters.adapt(wizardElement, IPluginContribution.class);
                                	statuses[0] = new Status(
                                            IStatus.ERROR,
                                            contribution != null ? contribution.getPluginId() : WorkbenchPlugin.PI_WORKBENCH,
                                            IStatus.OK,
                                            WorkbenchMessages.WorkbenchWizard_errorMessage,
                                            e);
                                }
                            }
                        });
                    }
                });
