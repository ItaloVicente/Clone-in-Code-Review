					if (allItem.getSelection())
						selectFindInItem(commentsItem);
					else if (commentsItem.getSelection())
						selectFindInItem(authorItem);
					else if (authorItem.getSelection())
						selectFindInItem(commitIdItem);
					else if (commitIdItem.getSelection())
						selectFindInItem(committerItem);
					else if (committerItem.getSelection())
						selectFindInItem(allItem);
