				int tcwidth = clientArea.width - marginWidth - marginWidth - thmargin - thmargin;
				if (toggleSize.x > 0)
					tcwidth -= toggleSize.x + IGAP;
				if (size.x > 0)
					tcwidth -= size.x + IGAP;
				tcwidth = Math.min(tcsize.x, tcwidth);
				if (tcwidth < 0)
					tcwidth = 0;
