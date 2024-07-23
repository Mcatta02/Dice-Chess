module com.view {
 
    requires java.desktop;


    opens com.view to javafx.fxml;
    exports com.view;
    exports com.view.GamePanels;
    opens com.view.GamePanels to javafx.fxml;


}