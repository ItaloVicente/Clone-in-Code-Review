				Iterator<IBrowserDescriptorWorkingCopy> iterator = browsersToCreate.iterator();
				while (iterator.hasNext()) {
					IBrowserDescriptorWorkingCopy browser2 = iterator
							.next();
					browser2.save();
				}
				tableViewer.refresh();
