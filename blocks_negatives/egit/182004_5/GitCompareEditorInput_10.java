				if (compareVersionIterator != null
						&& baseVersionIterator != null) {
					monitor.setTaskName(baseVersionIterator
							.getEntryPathString());
					add(result, baseVersionIterator.getEntryPathString(),
							new DiffNode(new FileRevisionTypedElement(compareRev, encoding),
									new FileRevisionTypedElement(baseRev, encoding)));
				} else if (baseVersionIterator != null
						&& compareVersionIterator == null) {
					monitor.setTaskName(baseVersionIterator
							.getEntryPathString());
					add(result, baseVersionIterator.getEntryPathString(),
							new DiffNode(Differencer.DELETION | Differencer.RIGHT, null, null,
									new FileRevisionTypedElement(baseRev, encoding)));
				} else if (compareVersionIterator != null
						&& baseVersionIterator == null) {
					monitor.setTaskName(compareVersionIterator
							.getEntryPathString());
					add(result, compareVersionIterator.getEntryPathString(),
							new DiffNode(Differencer.ADDITION | Differencer.RIGHT, null,
									new FileRevisionTypedElement(compareRev, encoding), null));
