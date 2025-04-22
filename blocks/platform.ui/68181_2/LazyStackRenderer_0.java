			MUIElement selectedElement = stack.getSelectedElement();
			if (selectedElement != null) {
				try {
					if (oldSel != null) {
						oldSel.getTransientData().put(PartServiceImpl.PERSP_DESELECTING, Boolean.TRUE);
					}
					lsr.showTab(selectedElement);
				} finally {
					if (oldSel != null) {
						oldSel.getTransientData().remove(PartServiceImpl.PERSP_DESELECTING);
					}
				}
			}
