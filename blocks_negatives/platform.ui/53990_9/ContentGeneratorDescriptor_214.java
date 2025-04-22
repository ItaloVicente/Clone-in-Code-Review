			groups = new TreeSet<>(new Comparator<MarkerGroup>() {
				@Override
				public int compare(MarkerGroup mg1, MarkerGroup mg2) {
					return mg1.getMarkerField().getName().compareTo(mg2.getMarkerField().getName());
				}
			});
