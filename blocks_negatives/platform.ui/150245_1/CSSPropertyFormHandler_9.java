					if (colors.size() > 0) {
						List<Integer> list = grad.getPercents();
						int[] percents = new int[list.size()];
						for (int i = 0; i < percents.length; i++) {
							percents[i] = list.get(i).intValue();
						}
						form.setTextBackground(colors.toArray(new Color[0]), percents,
								grad.getVerticalGradient());
