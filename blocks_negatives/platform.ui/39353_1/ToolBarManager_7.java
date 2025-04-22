				int newCount = toolBar.getItemCount();
				
				if (force)
					oldCount = newCount+1;
				
				relayout(toolBar, oldCount, newCount);
