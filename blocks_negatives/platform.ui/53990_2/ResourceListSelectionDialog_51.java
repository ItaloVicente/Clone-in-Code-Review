        BusyIndicator.showWhile(null, new Runnable() {
            @Override
			public void run() {
                descriptors = new ResourceDescriptor[resources.length];
                for (int i = 0; i < resources.length; i++) {
                    IResource r = resources[i];
                    ResourceDescriptor d = new ResourceDescriptor();
                    d.label = r.getName();
                    d.resources.add(r);
                    descriptors[i] = d;
                }
                Arrays.sort(descriptors);
                descriptorsSize = descriptors.length;

                int index = 0;
                if (descriptorsSize < 2) {
					return;
				}
                ResourceDescriptor current = descriptors[index];
                IResource currentResource = (IResource) current.resources
                        .get(0);
                for (int i = 1; i < descriptorsSize; i++) {
                    ResourceDescriptor next = descriptors[i];
                    IResource nextResource = (IResource) next.resources.get(0);
                    if (nextResource.getType() == currentResource.getType()
                            && next.label.equals(current.label)) {
                        current.resources.add(nextResource);
                        current.resourcesSorted = false;
                    } else {
                        if (current.resources.size() > 1) {
                            current.resourcesSorted = false;
                        }
                        descriptors[index + 1] = descriptors[i];
                        index++;
                        current = descriptors[index];
                        currentResource = (IResource) current.resources.get(0);
                    }
                }
                descriptorsSize = index + 1;
            }
        });
