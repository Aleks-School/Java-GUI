module com.github.programmeraleks.house {
    requires javafx.controls;
    requires java.desktop;
    requires javafx.fxml;

    opens com.github.programmeraleks.house to javafx.fxml;
    exports com.github.programmeraleks.house;
}