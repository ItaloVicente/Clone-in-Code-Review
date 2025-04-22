				if (ctrl instanceof CTabFolder) {
					CTabFolder ctf = (CTabFolder) ctrl;
					Rectangle bb = ctf.getBounds();
					bb.width--;
					ctf.setBounds(bb);
				}

