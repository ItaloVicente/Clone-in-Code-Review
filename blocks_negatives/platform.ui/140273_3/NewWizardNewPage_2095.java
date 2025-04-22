                    return wizardElement.createWizard();
                }
            };
            selectedWizards.put(selectedObject, selectedNode);
        }

        page.setCanFinishEarly(selectedObject.canFinishEarly());
        page.setHasPages(selectedObject.hasPages());
        page.selectWizardNode(selectedNode);

        updateDescription(selectedObject);
    }
