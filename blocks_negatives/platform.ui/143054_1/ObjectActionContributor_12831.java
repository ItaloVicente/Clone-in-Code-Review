        if (currentContribution.actions != null) {
            for (int i = 0; i < currentContribution.actions.size(); i++) {
                ActionDescriptor ad = (ActionDescriptor) currentContribution.actions
                        .get(i);
                String id = ad.getAction().getOverrideActionId();
                if (id != null) {
