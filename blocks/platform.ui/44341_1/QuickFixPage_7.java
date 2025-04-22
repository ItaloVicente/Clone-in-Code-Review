							IMarker marker = (IMarker) checked[i];
							monitor1.subTask(Util.getProperty(IMarker.MESSAGE, marker));
							resolution.run(marker);
							monitor1.worked(1);
						}
					}
