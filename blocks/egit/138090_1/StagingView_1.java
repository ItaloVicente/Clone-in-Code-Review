				setRedraw(false);
				try {
					refreshViewersInternal();
					unstagedViewer.setExpandedElements(unstagedExpanded);
					stagedViewer.setExpandedElements(stagedExpanded);
				} finally {
					setRedraw(true);
				}
