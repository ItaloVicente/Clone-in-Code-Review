					y = onBottom ? y - marginHeight - borderTop
							- (cornerSize / 4) : y - marginHeight - tabHeight
							- borderTop - (cornerSize / 4);
							height = height + borderBottom + borderTop + 2
									* marginHeight + tabHeight + cornerSize / 2
									+ cornerSize / 4
									+ (shadowEnabled ? BOTTOM_DROP_WIDTH : 0);
