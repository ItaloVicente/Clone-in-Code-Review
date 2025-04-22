					try {
						layoutUpdateInProgress = true;
						adjustWeights(sashesToDrag, e.x, e.y);
						host.layout();
						host.update();
					} finally {
						layoutUpdateInProgress = false;
					}
