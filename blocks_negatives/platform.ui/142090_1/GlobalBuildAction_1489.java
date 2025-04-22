                return ResourcesPlugin.FAMILY_MANUAL_BUILD == family;
            }
        };
        buildJob.setUser(true);
        buildJob.setProperty(IProgressConstants2.SHOW_IN_TASKBAR_ICON_PROPERTY, Boolean.TRUE);
        buildJob.schedule();
    }
