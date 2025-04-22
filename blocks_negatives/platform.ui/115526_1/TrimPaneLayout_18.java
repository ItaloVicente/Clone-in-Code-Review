		composite.addMouseTrackListener(new MouseTrackListener() {
			@Override
			public void mouseHover(MouseEvent e) {
			}

			@Override
			public void mouseExit(MouseEvent e) {
				Composite comp = (Composite) e.widget;
				comp.setCursor(null);
			}

			@Override
			public void mouseEnter(MouseEvent e) {
			}
		});
