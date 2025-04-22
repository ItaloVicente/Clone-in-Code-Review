        for (AboutBundleGroupData info : bundleGroupInfos) {
            String[] args = new String[] { info.getId(), info.getVersion(),
                    info.getName() };
            writer.println(NLS.bind(WorkbenchMessages.SystemSummary_featureVersion, args));
        }
    }
