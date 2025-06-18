module org.example.budgetbuddyprojectfrontend {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires okhttp3;
    requires org.json;
    requires java.sql;

    opens org.example.budgetbuddyprojectfrontend to javafx.fxml;
    exports org.example.budgetbuddyprojectfrontend;
}