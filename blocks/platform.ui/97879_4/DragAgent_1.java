			if (dragPH == null) {
				return;
			}

			if (dragPH != null) {
				dragPH.getParent().getChildren().remove(dragPH);
				dragPH = null;
			}
