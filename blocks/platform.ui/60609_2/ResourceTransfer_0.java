				String message = "Transfer aborted, too many resources: " + count + "."; //$NON-NLS-1$ //$NON-NLS-2$
				if (Util.isLinux()) {
					message += "\nIf you are running in x11vnc environment please consider to switch to vncserver " + //$NON-NLS-1$
							"+ vncviewer or to run x11vnc without clipboard support " + //$NON-NLS-1$
							"(use '-noclipboard' and '-nosetclipboard' arguments)."; //$NON-NLS-1$
				}
