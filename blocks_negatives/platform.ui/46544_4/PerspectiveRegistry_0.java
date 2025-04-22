					String label = perspective.getLocalizedLabel();
					String originalId = getOriginalId(perspective.getElementId());
					PerspectiveDescriptor originalDescriptor = descriptors.get(originalId);
					PerspectiveDescriptor newDescriptor = new PerspectiveDescriptor(id, label,
							originalDescriptor);
					descriptors.put(id, newDescriptor);
