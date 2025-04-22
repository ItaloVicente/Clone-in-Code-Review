				if (compareVersionIterator != null
						&& baseVersionIterator != null) {
					monitor.setTaskName(baseVersionIterator
							.getEntryPathString());
					add(result, baseVersionIterator.getEntryPathString(),
							new DiffNode(new FileRevisionTypedElement(compareRev, encoding),
									new FileRevisionTypedElement(baseRev, encoding)));
