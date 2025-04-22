				int tcwidth = clientArea.width - marginWidth - marginWidth - thmargin - thmargin;
				if (tsize.x > 0)
					tcwidth -= tsize.x + IGAP;
				if (size.x > 0)
					tcwidth -= size.x + IGAP;
				tcwidth = Math.min(tcsize.x, tcwidth);
				if (tcwidth < 0)
					tcwidth = 0;
