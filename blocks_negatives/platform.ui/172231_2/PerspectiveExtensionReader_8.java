				if (VAL_TRUE.equals(standalone)) {
					pageLayout.addStandaloneViewPlaceholder(id, intRelation, ratio, relative,
							!VAL_FALSE.equals(showTitle));
				} else {
					pageLayout.addPlaceholder(id, intRelation, ratio, relative);
				}
