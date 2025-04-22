     <T> void shiftNodeList(List<T> nodeList) {
         int shiftBy = (int) (nodeOffset++ % nodeList.size());
         for(int i = 0; i < shiftBy; i++) {
             T element = nodeList.remove(0);
             nodeList.add(element);
         }
     }

