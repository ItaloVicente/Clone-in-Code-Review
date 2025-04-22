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
				}));
