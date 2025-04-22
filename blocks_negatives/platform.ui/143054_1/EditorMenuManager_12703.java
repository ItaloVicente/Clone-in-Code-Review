                item.update(IContributionManagerOverrides.P_ENABLED);
            }
            if (wrappers != null) {
                for (int i = 0; i < wrappers.size(); i++) {
                    EditorMenuManager manager = (EditorMenuManager) wrappers
                            .get(i);
                    manager.setEnabledAllowed(enabledAllowed);
                }
            }
        }

        @Override
