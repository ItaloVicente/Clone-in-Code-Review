                       switch(count % 4) {
                           case 0: widget.computeSize(xSize, SWT.DEFAULT, flushState); break;
                           case 1: widget.computeSize(SWT.DEFAULT, ySize, flushState); break;
                           case 2: widget.computeSize(xSize, ySize, flushState); break;
                           case 3: widget.computeSize(SWT.DEFAULT, SWT.DEFAULT, flushState); break;
                       }
