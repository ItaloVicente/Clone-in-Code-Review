					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							blocked[0] = true;
							lock.acquire();
							lock.release();
							blocked[0] = false;
						}
