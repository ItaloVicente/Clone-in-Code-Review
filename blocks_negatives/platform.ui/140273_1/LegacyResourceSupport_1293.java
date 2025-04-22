            	resourceMappingAdapter = resourceAdapter;
            } else {
                resourceMappingAdapter = getDefaultContributorResourceAdapter();
                if (resourceMappingAdapter == null) {
                    return null;
                }
            }

