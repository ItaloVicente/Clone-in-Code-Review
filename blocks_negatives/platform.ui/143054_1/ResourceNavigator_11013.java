                    }
                }
            }
        }

        saveLinkingEnabled(memento);
    }

    /**
     * Saves the linking enabled state.
     */
    private void saveLinkingEnabled(IMemento memento) {
        memento.putInteger(
                IWorkbenchPreferenceConstants.LINK_NAVIGATOR_TO_EDITOR,
                linkingEnabled ? 1 : 0);
    }

    /**
     * Selects and reveals the specified elements.
     */
    @Override
