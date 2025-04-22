				Collections.sort(stack, (o1, o2) -> {
					MPart model1 = o1.getModel();
					MPart model2 = o2.getModel();

					/*
					 * WORKAROUND: Since we only have the activation list and not a bingToTop list,
					 * we can't set/know the order for inactive stacks. This workaround makes sure
					 * that the topmost part is at least at the first position.
					 */
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
