								System.out.println("got event");
								if (event.data == null) {
										event.detail = DND.DROP_NONE;
										return;
								}
								label.setText((String) event.data);
						}
				});
		}
