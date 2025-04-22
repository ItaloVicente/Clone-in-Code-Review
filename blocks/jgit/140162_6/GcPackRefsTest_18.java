						return super.isForceUpdate();
					}
				};
				update.setForceUpdate(true);
				update.setNewObjectId(b);
				return update.update();
