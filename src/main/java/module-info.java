module comp.graph.demo1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens comp.graph.demo1 to javafx.fxml;
    exports comp.graph.demo1;
}