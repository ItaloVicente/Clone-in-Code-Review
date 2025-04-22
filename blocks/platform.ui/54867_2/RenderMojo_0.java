        try{
            if (nativeWidthStr != "" && nativeHeightStr != ""){
                nativeWidth = Integer.parseInt(nativeWidthStr);
                nativeHeight = Integer.parseInt(nativeHeightStr);
            } else {
                String viewBoxStr = svgDocumentNode.getAttribute("viewBox");
                if (viewBoxStr == ""){
                    log.error("Icon defines neither width/height nor a viewBox, skipping: " + icon.nameBase);
                    failedIcons.add(icon);
                    return;
                }
                String[] splitted = viewBoxStr.split(" ");
                String xStr = splitted[0];
                String yStr = splitted[1];
                String widthStr = splitted[2];
                String heightStr = splitted[3];
                nativeWidth = Integer.parseInt(widthStr) - Integer.parseInt(xStr);
                nativeHeight = Integer.parseInt(heightStr) - Integer.parseInt(yStr); 
            }
        }catch (NumberFormatException e){
            log.error("Dimension could not be parsed ( "+e.getMessage()+ "), skipping: " + icon.nameBase);
            failedIcons.add(icon);
            return;
        }
