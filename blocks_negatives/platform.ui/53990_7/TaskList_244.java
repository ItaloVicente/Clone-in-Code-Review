			return adapterType.cast(new IShowInTargetList() {
                @Override
				public String[] getShowInTargetIds() {
                    return new String[] { IPageLayout.ID_RES_NAV };
                }

			});
