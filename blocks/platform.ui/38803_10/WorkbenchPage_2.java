				IActionSetDescriptor[] offActionSets = persp.getAlwaysOffActionSets();
				for (IActionSetDescriptor off : offActionSets) {
					if (off.getId().equals(desc.getId())) {
						return;
					}
				}
