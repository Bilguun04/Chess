package ca.mcgill.ecse.wareflow.application;

import ca.mcgill.ecse.wareflow.model.WareFlow;;

public class WareFlowApplication {

  private static WareFlow wareFlow;

  public static void main(String[] args) {
    // TODO Start the application user interface here
  }

  public static WareFlow getWareFlow() {
    if (wareFlow == null) {
      // these attributes are default, you should set them later with the setters
      wareFlow = new WareFlow();
    }
    return wareFlow;
  }

}
