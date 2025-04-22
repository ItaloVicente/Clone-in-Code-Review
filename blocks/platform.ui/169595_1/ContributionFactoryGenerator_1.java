		if (ici instanceof SubContributionItem) {
			ici = ((SubContributionItem) ici).getInnerItem();
		}
		if (ici instanceof MenuManager) {
			MMenu opaqueMenu = OpaqueElementUtil.createOpaqueMenu();
			opaqueMenu.setElementId(ici.getId());
			OpaqueElementUtil.setOpaqueItem(opaqueMenu, ici);
			return opaqueMenu;
		}
