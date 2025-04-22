		host.addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseExit(MouseEvent e) {
				host.setCursor(null);
			}
		});
