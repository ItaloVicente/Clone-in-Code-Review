        AboutInfo[] features = IDEWorkbenchPlugin.getDefault()
                .getFeatureInfos();
        for (AboutInfo info : features) {
            if (info.getFeatureId().equals(featureId)) {
                return info;
            }
        }
        return null;
    }
