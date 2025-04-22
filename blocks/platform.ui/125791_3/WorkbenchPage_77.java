				Collections.sort(stack, new Comparator<CompatibilityView>() {
					@Override
					public int compare(CompatibilityView o1, CompatibilityView o2) {
						MPart model1 = o1.getModel();
						MPart model2 = o2.getModel();

						if (model1 == topPart)
							return Integer.MIN_VALUE;
						if (model2 == topPart)
							return Integer.MAX_VALUE;

						int pos1 = activationList.indexOf(model1);
						int pos2 = activationList.indexOf(model2);
						if (pos1 == -1)
							pos1 = Integer.MAX_VALUE;
						if (pos2 == -1)
							pos2 = Integer.MAX_VALUE;
						return pos1 - pos2;
					}
