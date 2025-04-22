					Collections.sort(sections, new Comparator<Section>() {

						@Override
						public int compare(Section o1, Section o2) {
							return o1.name.compareTo(o2.name);
						}
					});
