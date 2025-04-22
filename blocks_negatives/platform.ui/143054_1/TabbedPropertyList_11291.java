			addMouseMoveListener(new MouseMoveListener() {

				@Override
				public void mouseMove(MouseEvent e) {
					if (!hover) {
						hover = true;
						redraw();
					}
