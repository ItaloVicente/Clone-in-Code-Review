			ResourceDescriptor current = descriptors[index];
			IResource currentResource = (IResource) current.resources.get(0);
			for (int i2 = 1; i2 < descriptorsSize; i2++) {
				ResourceDescriptor next = descriptors[i2];
				IResource nextResource = (IResource) next.resources.get(0);
				if (nextResource.getType() == currentResource.getType() && next.label.equals(current.label)) {
					current.resources.add(nextResource);
					current.resourcesSorted = false;
				} else {
					if (current.resources.size() > 1) {
						current.resourcesSorted = false;
					}
					descriptors[index + 1] = descriptors[i2];
					index++;
					current = descriptors[index];
					currentResource = (IResource) current.resources.get(0);
				}
			}
			descriptorsSize = index + 1;
