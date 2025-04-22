							control.dispose();
						}
						availableShells.add(s);
						s.setVisible(false);
					} else {
						s.setData(CLOSE_LISTENER, l);
					}
				}
			}
			e.doit = false;
		}
	};

	public ShellPool(Shell parentShell, int childFlags) {
		this.parentShell = parentShell;
		this.flags = childFlags;
	}

	public Shell allocateShell(ShellListener closeListener) {
		Shell result;
		if (!availableShells.isEmpty()) {
			result = (Shell) availableShells.removeFirst();
		} else {
			result = new Shell(parentShell, flags);
			result.addShellListener(this.closeListener);
			result.addDisposeListener(disposeListener);
		}

		result.setData(CLOSE_LISTENER, closeListener);
		return result;
	}

	public void dispose() {
		for (Iterator iter = availableShells.iterator(); iter.hasNext();) {
			Shell next = (Shell) iter.next();
			next.removeDisposeListener(disposeListener);

			next.dispose();
		}

		availableShells.clear();
		isDisposed = true;
	}
