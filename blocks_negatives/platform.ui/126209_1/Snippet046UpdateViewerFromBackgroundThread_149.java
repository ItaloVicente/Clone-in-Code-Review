							v.getTable().getDisplay().asyncExec(new Runnable() {

								@Override
								public void run() {
									model[j].finished = true;
									v.update(model[j], null);
								}

