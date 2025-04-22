				if (prevCssCls == null || !prevCssCls.equals(WidgetElement.getCSSClass(cti))) {
					reapplyStyles(cti.getParent());
				}
			} else {
				if (partActivatedEvent) {
					return;
				}
